package com.example;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;

public class ReflectiveHttpClientCaller {

    public static void main(String[] args) throws Exception{

        // Attempt to load TestHttpClient class without URLClassLoader
        try {
            Class.forName("com.example.TestHttpClient");
            System.err.println("TestHttpClient is in the classpath - oops ");
            System.exit(10);
        } catch (ClassNotFoundException e) {
            System.out.println("TestHttpClient is NOT in ordinarily the classpath, good.");
        }

        // Proceed with URLClassLoader
        File jarFile = new File("test/target/test-1.0-SNAPSHOT.jar");
        URL[] urls = {jarFile.toURI().toURL()};

        PermissionCollection permissions = new Permissions();
        permissions.add(new URLPermission("https://httpbin.org/get", "GET:Accept"));

        URLClassLoader classLoader = new URLClassLoader(urls) {
            @Override
            protected PermissionCollection getPermissions(CodeSource codesource) {
                return super.getPermissions(codesource);
            }
        };

        // Load the TestHttpClient class
        Class<?> testHttpClientClass = classLoader.loadClass("com.example.TestHttpClient");

        // Create an instance of TestHttpClient
        Object testHttpClientInstance = testHttpClientClass.getDeclaredConstructor().newInstance();

        // Get the fetchAndValidateResponse method
        Method method = testHttpClientClass.getMethod("fetchAndValidateResponse");

        // Invoke the method and print the result
        boolean result = (boolean) method.invoke(testHttpClientInstance);
        System.out.println("Response is valid JSON: " + result);

        classLoader.close();
    }
}
