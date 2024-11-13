package dev.mightyelephants.backend.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;

@Service
public class GeocodingService {

    private static final String API_KEY = "e74d67f92b0045db9578bf8b9ead951f";

    public double[] geocodeAddress(String address) {
        try {
            // Build the URL
            String url = UriComponentsBuilder.fromHttpUrl("https://api.geoapify.com/v1/geocode/search")
                    .queryParam("text", address)
                    .queryParam("apiKey", API_KEY)
                    .toUriString();

            // Create an HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Create an HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error during geocoding: HTTP " + response.statusCode());
            }

            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            GeoapifyResponse geoapifyResponse = objectMapper.readValue(response.body(), GeoapifyResponse.class);

            if (geoapifyResponse == null || geoapifyResponse.getFeatures().isEmpty()) {
                throw new RuntimeException("Address not found");
            }

            // Extract coordinates
            List<Double> coordinates = geoapifyResponse.getFeatures().get(0).getGeometry().getCoordinates();
            return new double[]{coordinates.get(1), coordinates.get(0)}; // [latitude, longitude]
        } catch (Exception e) {
            throw new RuntimeException("Error during geocoding: " + e.getMessage(), e);
        }
    }

    // Embedded DTO classes
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GeoapifyResponse {
        private List<Feature> features;

        public List<Feature> getFeatures() {
            return features;
        }

        public void setFeatures(List<Feature> features) {
            this.features = features;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Feature {
        private Geometry geometry;

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Geometry {
        private List<Double> coordinates;

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }
    }
}
