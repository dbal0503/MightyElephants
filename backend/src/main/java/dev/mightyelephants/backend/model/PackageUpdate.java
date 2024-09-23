package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

// We would need to fix this entity and add the real time tracking info

@Entity
@Table(name = "package_updates")
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

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }
}
