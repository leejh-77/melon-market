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

    @Override
    public List<Post> findRecentPosts(int count, String queryText) {
        TypedQuery<Post> query;
        if (queryText != null) {
            query = this.createRecentPostsQueryWithSearchText(queryText);
        } else {
            query = this.createRecentPostsQuery();
        }
        query.setMaxResults(count);
        return query.getResultList();
    }

    private TypedQuery<Post> createRecentPostsQuery() {
        return this.em.createQuery(
                "SELECT p FROM Post p ORDER BY p.createdTime DESC", Post.class);
    }

    private TypedQuery<Post> createRecentPostsQueryWithSearchText(String queryText) {
        TypedQuery<Post> query = this.em.createQuery(
                "SELECT p FROM Post p WHERE p.title LIKE :queryText" +
                        " ORDER BY p.createdTime DESC", Post.class);
        query.setParameter("queryText", "%" + queryText + "%");
        return query;
    }
}
