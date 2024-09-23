package dev.mightyelephants.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "deliverers")
public class Deliverer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long delivererId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private int numberOfPackages;

    public Deliverer() {
    }

    public Deliverer(String fullName, int numberOfPackages) {
        this.fullName = fullName;
        this.numberOfPackages = numberOfPackages;
    }

    public Long getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Long delivererId) {
        this.delivererId = delivererId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }
}
