package com.example.shop.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

public class HttpClientUtil {

    public static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static HttpRequest getRequest(String uri) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

    }
}