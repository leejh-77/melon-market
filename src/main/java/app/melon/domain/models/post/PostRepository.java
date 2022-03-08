package app.melon.domain.models.post;

import app.melon.domain.models.HibernateRepository;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostRepository extends HibernateRepository<Post> {

    public PostRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Post> findTopPosts(int count) {
        String queryStr = "FROM " + super.getTableName() + " ORDER BY created_time DESC";
        Query<Post> query = super.getSession().createQuery(queryStr);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<Post> findLikePosts(int count, User user) {
        String queryStr = "FROM " + super.getTableName()
                + " WHERE id IN (SELECT post FROM PostLike WHERE user = ?0) ORDER BY created_time DESC";
        Query<Post> query = super.getSession().createQuery(queryStr);
        query.setParameter(0, user);
        query.setMaxResults(count);
        return query.getResultList();
    }
}
