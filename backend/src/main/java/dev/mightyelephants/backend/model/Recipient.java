package dev.mightyelephants.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "recipients")
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

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
