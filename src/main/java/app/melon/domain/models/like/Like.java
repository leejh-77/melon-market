package app.melon.domain.models.like;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "user_id")
    private long userId;

    public Like(long id, long postId, long userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }
}
