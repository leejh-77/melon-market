package app.melon.domain.models.like;

import app.melon.domain.models.HibernateRepository;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;

@Repository
public class LikeRepository extends HibernateRepository<Like> {

    public LikeRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Like findByUserIdAndPostId(long userId, long postId) {
        return super.findOne("userId = ?0 AND postId = ?1", userId, postId);
    }

    public int findCountByPostId(long id) {
        NativeQuery<?> query = super.getSession()
                .createNativeQuery("SELECT COUNT(*) FROM post_like WHERE post_id = ?0");
        query.setParameter(0, id);
        return ((BigInteger) query.uniqueResult()).intValue();
    }
}
