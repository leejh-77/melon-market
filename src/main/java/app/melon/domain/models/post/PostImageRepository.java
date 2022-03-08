package app.melon.domain.models.post;

import app.melon.domain.models.HibernateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostImageRepository extends HibernateRepository<PostImage> {

    public PostImageRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public PostImage findImageByPostId(long id) {
        return super.findOne("post_id = ?0", id);
    }

    public List<PostImage> findImagesByPostId(long id) {
        return super.findAll("post_id = ?0", id);
    }
}
