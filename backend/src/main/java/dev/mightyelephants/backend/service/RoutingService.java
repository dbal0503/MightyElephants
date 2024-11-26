package dev.mightyelephants.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Map;

@Service
public class RoutingService {

    private static final String API_KEY = "e74d67f92b0045db9578bf8b9ead951f";

    public Map<String, Object> getRoute(double[] startCoords, double[] endCoords) {
        try {
            String waypoints = String.format("%f,%f|%f,%f", startCoords[0], startCoords[1], endCoords[0], endCoords[1]);

            String url = UriComponentsBuilder.fromHttpUrl("https://api.geoapify.com/v1/routing")
                    .queryParam("waypoints", waypoints)
                    .queryParam("mode", "drive")
                    .queryParam("apiKey", API_KEY)
                    .toUriString();

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error during routing: HTTP " + response.statusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), Map.class);

        } catch (Exception e) {
            throw new RuntimeException("Error during routing: " + e.getMessage(), e);
        }
    }
}
