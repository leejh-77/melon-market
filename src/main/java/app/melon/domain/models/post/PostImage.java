package app.melon.domain.models.post;

import app.melon.domain.models.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post_image")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public PostImage(){}

    public PostImage(long postId, String imageName) {
        this.postId = postId;
        this.imageName = imageName;
        this.createdTime = LocalDateTime.now();
    }
}
