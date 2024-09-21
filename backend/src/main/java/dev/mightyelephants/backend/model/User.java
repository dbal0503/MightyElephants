package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")

public class User {
    @Id // Marks the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates an ID using the database's identity column
    private Long id;

    @Column(nullable = false, unique = true) // Defines a column, makes it non-null and unique
    private String username;

    @Column(nullable = false) // Non-null field for the user's password
    private String password;

    @Column(nullable = false) // Non-null field for the user's email
    private String email;

    @Column(name = "date_of_birth") // Optional: customizing the column name
    private LocalDate dateOfBirth;

    // Constructors, Getters and Setters

    public User() {
    }

    public User(String username, String password, String email, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
