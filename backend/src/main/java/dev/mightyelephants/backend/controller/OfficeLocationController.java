package dev.mightyelephants.backend.controller;

import dev.mightyelephants.backend.model.OfficeLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.mightyelephants.backend.service.OfficeLocationService;


@RestController
@RequestMapping("/api/office-locations")
public class OfficeLocationController {

    private final OfficeLocationService officeLocationService;

    @Autowired
    public OfficeLocationController(OfficeLocationService officeLocationService) {
        this.officeLocationService = officeLocationService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<OfficeLocation> getNearestOfficeLocation(@RequestParam double lat, @RequestParam double lon) {
        OfficeLocation nearestOfficeLocation = officeLocationService.findNearestOfficeLocation(lat, lon);
        return ResponseEntity.ok(nearestOfficeLocation);
    }
}
