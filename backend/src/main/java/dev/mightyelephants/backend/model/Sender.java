package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "senders")
@Getter
@Setter
public class Sender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long senderId;

    @Column(nullable = false)
    private String fullName;

    public Sender() {
    }

    public Sender(String fullName) {
        this.fullName = fullName;
    }
}
