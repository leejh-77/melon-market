package app.melon.domain.models.user;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "email_address", nullable = false, length = 100, unique = true)
    private String emailAddress;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "image_path")
    private String imagePath;

    public User() {
    }

    public User(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.createdDate = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}