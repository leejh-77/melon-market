package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike findByUserIdAndPostId(long userId, long postId);

    int countByPostId(long id);
}
