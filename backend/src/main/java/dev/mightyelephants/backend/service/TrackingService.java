package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.ShippingLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ShippingLabelService shippingLabelService;

    public void startTracking(String trackingNumber, List<double[]> routeCoordinates) {
        new Thread(() -> {
            for (double[] coord : routeCoordinates) {
                messagingTemplate.convertAndSend("/topic/tracking/" + trackingNumber, coord);
                try {
                    Thread.sleep(2000); // Simulating 2s delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            // Notify tracking completion
            messagingTemplate.convertAndSend("/topic/tracking/" + trackingNumber, "Tracking Completed");
            onTrackingComplete(trackingNumber);
        }).start();
    }

    private void onTrackingComplete(String trackingNumber) {
        System.out.println("Tracking completed for: " + trackingNumber);
        ShippingLabel sL = shippingLabelService.getShippingLabelByTrackingNumber(trackingNumber);
        deliveryService.completeDelivery(sL.getId());
        // Example: Update database, log tracking completion, notify external services, etc.
    }
}
