package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.Post;
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
    public List<Post> findLikedPosts(int count, long userId, String region) {
        String q = "SELECT p FROM Post p WHERE p.id IN (SELECT pl.post FROM PostLike pl WHERE pl.user.id = :user_id)";
        if (region != null) {
            q += " AND p.region.code LIKE :region";
        }
        q += "ORDER BY p.createdTime DESC";
        TypedQuery<Post> query = this.em.createQuery(q, Post.class);
        query.setParameter("user_id", userId);
        if (region != null) {
            query.setParameter("region", region + '%');
        }
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public List<Post> findRecentPosts(int count, String queryText, String region) {
        String q = "SELECT p FROM Post p";
        if (queryText != null) {
            q += " WHERE p.title LIKE :queryText";
        }
        if (region != null) {
            if (queryText == null) {
                q += " WHERE";
            } else {
                q += " AND";
            }
            q += " p.region.code LIKE :region";
        }
        q += " ORDER BY p.createdTime DESC ";
        TypedQuery<Post> query = this.em.createQuery(q, Post.class);
        if (queryText != null) {
            query.setParameter("queryText", '%' + queryText + '%');
        }
        if (region != null) {
            query.setParameter("region", region + '%');
        }
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public List<Post> findMyPosts(int listQueryCount, long userId) {
        TypedQuery<Post> query = this.em.createQuery("SELECT p FROM Post p" +
                " WHERE p.user.id = :userId ORDER BY p.createdTime DESC", Post.class);
        query.setMaxResults(listQueryCount);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
