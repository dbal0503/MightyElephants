package dev.mightyelephants.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class RoutingService {

    private static final String API_KEY = "";

    public Map<String, Object> getRoute(double[] startCoords, double[] endCoords) {
        RestTemplate restTemplate = new RestTemplate();
        String waypoints = String.format("%f,%f|%f,%f", startCoords[0], startCoords[1], endCoords[0], endCoords[1]);
        String url = UriComponentsBuilder.fromHttpUrl("https://api.geoapify.com/v1/routing")
                .queryParam("waypoints", waypoints)
                .queryParam("mode", "drive")
                .queryParam("apiKey", API_KEY)
                .toUriString();

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }
}
