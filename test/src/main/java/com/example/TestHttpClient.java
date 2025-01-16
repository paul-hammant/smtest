package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestHttpClient {

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://httpbin.org/get"))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.body().startsWith("{")) {
                System.out.println("Response body starts with '{'");
            } else {
                System.out.println("Response body does not start with '{'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
