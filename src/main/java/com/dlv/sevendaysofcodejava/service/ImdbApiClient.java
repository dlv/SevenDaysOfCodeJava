package com.dlv.sevendaysofcodejava.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImdbApiClient {

    private final String apiKey;
    private final String address;

    public ImdbApiClient(String apiKey, String address) {
        this.apiKey = apiKey;
        this.address = address;
    }

    public String getBody(String id) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = this.address + "?i=" + id + "&apikey=" + this.apiKey;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
