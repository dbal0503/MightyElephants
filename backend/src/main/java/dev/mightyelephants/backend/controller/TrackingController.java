package dev.mightyelephants.backend.controller;

import dev.mightyelephants.backend.service.GeocodingService;
import dev.mightyelephants.backend.service.RoutingService;
import dev.mightyelephants.backend.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TrackingController {

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private RoutingService routingService;

    @Autowired
    private TrackingService trackingService;

    @PostMapping("/start-tracking")
    public ResponseEntity<Void> startTracking(@RequestBody Map<String, String> payload) {
        String trackingNumber = payload.get("trackingNumber");
        String startAddress = payload.get("startAddress");
        System.out.println("startAddress: " + startAddress);
        String endAddress = payload.get("endAddress");

        double[] startCoords = geocodingService.geocodeAddress(startAddress);
        System.out.println("found start address");
        double[] endCoords = geocodingService.geocodeAddress(endAddress);
        System.out.println("found end address");

        Map<String, Object> routeData = routingService.getRoute(startCoords, endCoords);
        List<double[]> routeCoordinates = extractCoordinates(routeData);

        trackingService.startTracking(trackingNumber, routeCoordinates);
        return ResponseEntity.ok().build();
    }

    private List<double[]> extractCoordinates(Map<String, Object> routeData) {
        List<double[]> coordinatesList = new ArrayList<>();
        List<Map<String, Object>> features = (List<Map<String, Object>>) routeData.get("features");

        if (!features.isEmpty()) {
            Map<String, Object> geometry = (Map<String, Object>) features.get(0).get("geometry");
            List<List<List<Double>>> coordinates = (List<List<List<Double>>>) geometry.get("coordinates");

            for (List<List<Double>> line : coordinates) {
                for (List<Double> point : line) {
                    coordinatesList.add(new double[]{point.get(1), point.get(0)}); // [latitude, longitude]
                }
            }
        }

        return coordinatesList;
    }
}

