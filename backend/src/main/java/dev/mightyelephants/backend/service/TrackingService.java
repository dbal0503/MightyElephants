package dev.mightyelephants.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void startTracking(String trackingNumber, List<double[]> routeCoordinates) {
        new Thread(() -> {
            for (double[] coord : routeCoordinates) {
                messagingTemplate.convertAndSend("/topic/tracking/" + trackingNumber, coord);
                try {
                    Thread.sleep(2000); //2s
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
