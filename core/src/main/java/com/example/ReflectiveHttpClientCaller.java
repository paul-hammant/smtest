package com.example;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;

public class ReflectiveHttpClientCaller {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {

        // Attempt to load TestHttpClient class without URLClassLoader
        try {
            Class.forName("com.example.TestHttpClient");
            System.err.println("Test: TestHttpClient is in the classpath - oops ");
            System.exit(10);
        } catch (ClassNotFoundException e) {
            System.out.println("Test: TestHttpClient is NOT in ordinarily the base classpath, good.");
        }

        // Proceed with URLClassLoader
        File jarFile = new File("test/target/test-1.0-SNAPSHOT.jar");
        URL[] urls = {jarFile.toURI().toURL()};

        PermissionCollection permissions = new Permissions();

        if (args.length > 0 && args[0].equals("--WithGetAccessPermissionGrantInJava")) {
            System.out.println("Test: Explicit GRANTING of permissions in Java, because of --WithGetAccessPermissionGrantInJava " +
                    "command line option");
            permissions.add(new URLPermission("https://httpbin.org/get", "GET:Accept"));
            permissions.add(new SocketPermission("httpbin.org", "resolve"));
        } else {
            System.out.println("Test: NO explicit GRANTING permissions in Java without --WithGetAccessPermissionGrantInJava " +
                    "command line option, See README.md");

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

        System.out.println("\nTest: Setup complete; parent classload has a few explicit permissions courtesy of `security.policy` " +
                "file in repo; now testing reflection-based invocation of TestHttpClient.fetchAndValidateResponse() in a child " +
                "classloader who's jar is not mentioned in the same `security.policy` file in repo ....\n");


        // Invoke the method and print the result
        String result = (String) testHttpClientClass.getMethod("fetchAndValidateResponse").invoke(testHttpClientInstance);
        System.out.println("Test: Class that was dynamically loaded from a child classloader, on a reflection-invcation of a permission-needing method (get string from httpbin.org/get) encountered: " + result);

        classLoader.close();
    }
}
