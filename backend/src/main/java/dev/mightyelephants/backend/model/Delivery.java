package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    public Delivery() {
    }

    public Delivery(String status, String trackingNumber, Sender sender, Recipient recipient) {
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
