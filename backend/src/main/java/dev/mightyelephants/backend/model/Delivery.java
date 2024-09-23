package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
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
}
