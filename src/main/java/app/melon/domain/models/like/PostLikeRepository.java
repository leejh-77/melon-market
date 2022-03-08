package app.melon.domain.models.like;

import app.melon.domain.models.HibernateRepository;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;

@Repository
public class PostLikeRepository extends HibernateRepository<PostLike> {

    public PostLikeRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public PostLike findByUserIdAndPostId(long userId, long postId) {
        return super.findOne("user_id = ?0 AND post_id = ?1", userId, postId);
    }

    public int findCountByPostId(long id) {
        NativeQuery<?> query = super.getSession()
                .createNativeQuery("SELECT COUNT(*) FROM post_like WHERE post_id = ?0");
        query.setParameter(0, id);
        return ((BigInteger) query.uniqueResult()).intValue();
    }
}
