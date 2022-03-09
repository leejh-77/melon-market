package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PostRepositoryExtensionImpl implements PostRepositoryExtension {

    private final EntityManager em;

    public PostRepositoryExtensionImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Post> findLikedPosts(int count, long userId) {
        TypedQuery<Post> query = this.em.createQuery("SELECT p FROM Post p WHERE p.id IN " +
                "(SELECT pl.post FROM PostLike pl WHERE pl.user.id = :user_id) ORDER BY p.createdTime DESC", Post.class);
        query.setParameter("user_id", userId);
        query.setMaxResults(count);
        return query.getResultList();
    }
}
