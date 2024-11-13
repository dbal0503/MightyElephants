package dev.mightyelephants.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {

    private static final String API_KEY = "";

    public double[] geocodeAddress(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl("https://api.geoapify.com/v1/geocode/search")
                .queryParam("text", address)
                .queryParam("apiKey", API_KEY)
                .toUriString();

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> features = (List<Map<String, Object>>) response.getBody().get("features");

        if (!features.isEmpty()) {
            Map<String, Object> geometry = (Map<String, Object>) features.get(0).get("geometry");
            List<Double> coordinates = (List<Double>) geometry.get("coordinates");
            return new double[]{coordinates.get(1), coordinates.get(0)}; // [latitude, longitude]
        } else {
            throw new RuntimeException("Address not found");
        }
    }
}
