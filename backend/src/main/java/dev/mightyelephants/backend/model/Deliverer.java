package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "deliverers")
@Getter
@Setter
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
}
