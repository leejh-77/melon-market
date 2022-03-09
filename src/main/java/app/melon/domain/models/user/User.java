package app.melon.domain.models.user;

import app.melon.domain.models.post.Post;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email_address", nullable = false, length = 100, unique = true)
    private String emailAddress;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private final List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String emailAddress, String username, String password) {
        this.emailAddress = emailAddress;
        this.username = username;
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

    public void setImageUrl(String imagePath) {
        this.imageUrl = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
