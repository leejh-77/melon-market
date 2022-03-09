package app.melon.domain.models.post;

import app.melon.domain.models.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PostRepositoryExtensionImpl implements PostRepositoryExtension {

    private final EntityManager em;

    public PostRepositoryExtensionImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Post> findLikedPosts(int count, User user) {
        TypedQuery<Post> query = this.em.createQuery("SELECT p FROM Post p WHERE p.id IN " +
                "(SELECT pl.post FROM PostLike  pl WHERE p.user = :user) ORDER BY p.createdTime DESC", Post.class);
        query.setParameter("user", user);
        query.setMaxResults(count);
        return query.getResultList();
    }
}
