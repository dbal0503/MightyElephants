package dev.mightyelephants.backend.service;

import java.awt.geom.Point2D;
import dev.mightyelephants.backend.model.OfficeLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.mightyelephants.backend.repository.OfficeLocationRepository;

import java.util.List;

@Service
public class OfficeLocationService {

    private final OfficeLocationRepository officeLocationRepository;

    @Autowired
    public OfficeLocationService(OfficeLocationRepository officeLocationRepository) {
        this.officeLocationRepository = officeLocationRepository;
    }

    public OfficeLocation findNearestOfficeLocation(double userLat, double userLon) {
        List<OfficeLocation> officeLocations = officeLocationRepository.findAll();
        OfficeLocation nearestOfficeLocation = null;
        double minDistance = Double.MAX_VALUE;

        for (OfficeLocation officeLocation : officeLocations) {
            double distance = calculateDistance(userLat, userLon, officeLocation.getLatitude(), officeLocation.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestOfficeLocation = officeLocation;
            }
        }

        return nearestOfficeLocation;
    }

    //to find the closest office to address client enters
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return Point2D.distance(lat1, lon1, lat2, lon2) * 111; // Approximate each degree as 111 km
    }

}
