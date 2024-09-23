package dev.mightyelephants.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "senders")
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

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
