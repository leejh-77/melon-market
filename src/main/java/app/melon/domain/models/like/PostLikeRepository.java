package app.melon.domain.models.like;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike findByUserIdAndPostId(long userId, long postId);

    int countByPostId(long id);
}
