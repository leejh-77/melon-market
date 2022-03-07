package app.melon.domain.models.post;

import app.melon.domain.models.HibernateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PostImageRepository extends HibernateRepository<PostImage> {

    public PostImageRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public PostImage findImageByPostId(long id) {
        return super.find("post_id = ?0", id);
    }
}
