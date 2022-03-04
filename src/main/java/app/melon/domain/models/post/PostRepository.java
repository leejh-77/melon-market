package app.melon.domain.models.post;

import app.melon.domain.models.HibernateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PostRepository extends HibernateRepository<Post> {

    public PostRepository(EntityManager entityManager) {
        super(entityManager);
    }
}
