package app.melon.domain.models.post;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@lombok.Builder(builderClassName = "Builder", toBuilder = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private LocalDateTime createdTime;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "view_count")
    private long viewCount;

    public Post(long id, String title, String body, int price, LocalDateTime createdTime, long userId, long viewCount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.price = price;
        this.createdTime = createdTime;
        this.userId = userId;
        this.viewCount = viewCount;
    }
}
