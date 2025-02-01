package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import static java.security.AccessController.doPrivileged;

public class TestHttpClient {

    static class FooClassLoader extends URLClassLoader{

        public FooClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }
    }

    // Called using reflection from ReflectiveHttpClientCaller
    public String fetchAndValidateResponse() throws IOException, InterruptedException, URISyntaxException {

        // Historical vulns with Java sandbox/ permissions
        try {
            ProtectionDomain foo = this.getClass().getProtectionDomain();
            throw new AssertionError("nope");
        } catch (AccessControlException e) {
            // expected
        }

        URLClassLoader cl = ((URLClassLoader) this.getClass().getClassLoader());

        try {
            new FooClassLoader(new URL[0], this.getClass().getClassLoader());
            throw new AssertionError("nope");
        } catch (AccessControlException e) {
            // expected
        }

        // Add more from https://github.com/pwntester/ysoserial here....


        HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://httpbin.org/get"))
                    .header("Accept", "application/json")
                    .build();
        try {
            String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            //System.out.println("body: " + body);
            return body.contains("{\n" +
                    "  \"args\": {}, \n" +
                    "  \"headers\": {\n" +
                    "    \"Accept\": \"application/json\", \n" +
                    "    \"Host\": \"httpbin.org\", \n" +
                    "    \"User-Agent\": \"Java-http-client") ? "JSON as expected": "Not the expected JSON : " + body;
        } catch (SecurityException e) {
            return "Security Exception, msg: " + e.getMessage();
        }


    }
//    public String fetchAndValidateResponse() throws IOException, InterruptedException, URISyntaxException {
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI("https://httpbin.org/get"))
//                    .header("Accept", "application/json")
//                    .build();
//        return AccessController.doPrivileged(new PrivilegedAction<String>() {
//            @Override
//            public String run() {
//                try {
//                    String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
//                    //System.out.println("body: " + body);
//                    return body.contains("{\n" +
//                            "  \"args\": {}, \n" +
//                            "  \"headers\": {\n" +
//                            "    \"Accept\": \"application/json\", \n" +
//                            "    \"Host\": \"httpbin.org\", \n" +
//                            "    \"User-Agent\": \"Java-http-client") ? "JSON as expected": "Not the expected JSON : " + body;
//                } catch (SecurityException | IOException | InterruptedException e) {
//                    return "Security Exception, msg: " + e.getMessage();
//                }
//            }
//        });
//
//    }


}
