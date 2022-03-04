package app.melon.domain.models.post;

import app.melon.domain.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body")
    private String body;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
