package app.melon.domain.models.post;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "price",  nullable = false)
    private int price;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "user_id")
    private long userId;

    public Post(){}

    public Post(String title, String body, int price, long userId) {
        this.title = title;
        this.body = body;
        this.price = price;
        this.userId = userId;
        this.createdDate = LocalDateTime.now();
    }
}
