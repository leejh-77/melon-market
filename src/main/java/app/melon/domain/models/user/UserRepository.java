package app.melon.domain.models.user;

import app.melon.domain.models.HibernateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends HibernateRepository<User> {

    public UserRepository(EntityManager entityManager) {
        super(entityManager);
    }
}
