package com.example;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;

public class ReflectiveHttpClientCaller {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // Attempt to load TestHttpClient class without URLClassLoader
        try {
            Class.forName("com.example.TestHttpClient");
            System.err.println("TestHttpClient is in the classpath - oops ");
            System.exit(10);
        } catch (ClassNotFoundException e) {
            System.out.println("TestHttpClient is NOT in ordinarily the base classpath, good.");
        }

        // Proceed with URLClassLoader
        File jarFile = new File("test/target/test-1.0-SNAPSHOT.jar");
        URL[] urls = {jarFile.toURI().toURL()};

        PermissionCollection permissions = new Permissions();


        if (args.length > 0 && args[0].equals("--WithGetAccessPermissionGrantInJava")) {
            System.out.println("Explicit GRANTING of `new URLPermission(\"https://httpbin.org/get\", \"GET:Accept\")` in Java, because of --WithGetAccessPermissionGrantInJava option");
            permissions.add(new URLPermission("https://httpbin.org/get", "GET:Accept"));
        } else {
            System.out.println("NO explicit GRANTING permissions in Java withough --WithGetAccessPermissionGrantInJava option, See README.md");

        }

        URLClassLoader classLoader = new URLClassLoader(urls) {
            @Override
            protected PermissionCollection getPermissions(CodeSource codesource) {
                return permissions;
            }
        };

        // Load the TestHttpClient class
        Class<?> testHttpClientClass = classLoader.loadClass("com.example.TestHttpClient");

        // Create an instance of TestHttpClient
        Object testHttpClientInstance = testHttpClientClass.getDeclaredConstructor().newInstance();

        System.out.println("Setup complete, now testing reflection-based invocation of TestHttpClient.fetchAndValidateResponse()....");


        // Invoke the method and print the result
        try {
            // Get the fetchAndValidateResponse method
            Method method = testHttpClientClass.getMethod("fetchAndValidateResponse");
            boolean result = (boolean) method.invoke(testHttpClientInstance);
            if (result) {
                System.out.println("Class that was dynamically loaded from a child classloader was able to go to HttpBin.org and get a JSON response");
            } else {
                System.out.println("Not JSON???");
            }
        } catch (InvocationTargetException ite) {

            if (ite.getCause() instanceof java.lang.SecurityException
                    && ite.getCause().getMessage().equals("access denied (\"java.net.URLPermission\" \"https://httpbin.org/get\" \"GET:Accept\")")) {
                System.out.println("Class that was dynamically loaded from a child classloader was NOT able to go to HttpBin.org and get a JSON response. ");
            } else {
                System.out.println("some unexpected ITE? " + ite.getCause().getMessage());
            }
        }

        classLoader.close();
    }
}
