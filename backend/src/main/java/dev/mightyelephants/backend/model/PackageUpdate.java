package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// We would need to fix this entity and add the real time tracking info

@Entity
@Table(name = "package_updates")
@Getter 
@Setter
public class PackageUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime estimatedDelivery;

    public PackageUpdate() {
    }

    public PackageUpdate(String status, LocalDateTime estimatedDelivery) {
        this.status = status;
        this.estimatedDelivery = estimatedDelivery;
    }
}
