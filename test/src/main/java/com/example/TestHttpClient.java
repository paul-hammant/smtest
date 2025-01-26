package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestHttpClient {

    public String fetchAndValidateResponse() throws IOException, InterruptedException, URISyntaxException {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://httpbin.org/get"))
                    .header("Accept", "application/json")
                    .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (SecurityException e) {
            return "Security Exception, msg: " + e.getMessage();
        }

        String body = response.body();
        //System.out.println("body: " + body);
        return body.contains("{\n" +
                "  \"args\": {}, \n" +
                "  \"headers\": {\n" +
                "    \"Accept\": \"application/json\", \n" +
                "    \"Host\": \"httpbin.org\", \n" +
                "    \"User-Agent\": \"Java-http-client") ? "JSON as expected": "Not the expected JSON : " + body;
    }
}
