package com.example;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflectiveHttpClientCaller {

    public static void main(String[] args) {
        try {
            // Path to the test module's JAR file
            File jarFile = new File("test/target/test-1.0-SNAPSHOT.jar");
            URL[] urls = {jarFile.toURI().toURL()};
            URLClassLoader classLoader = new URLClassLoader(urls);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
