package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipients")
@Getter
@Setter
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipientId;

    @Column(nullable = false)
    private String fullName;

    public Recipient() {
    }

    public Recipient(String fullName) {
        this.fullName = fullName;
    }
}
