package app.melon.domain.models;

import app.melon.domain.models.user.User;
import app.melon.domain.models.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class HibernateRepositoryTest {

    @TestConfiguration
    public static class UserRepositoryTestConfiguration {
        @Bean
        public UserRepository userRepository(EntityManager entityManager) {
            return new UserRepository(entityManager);
        }
    }

    @Autowired
    private UserRepository repository;

    @Test
    public void nullUsername_shouldFail() {
        User user = new User(null, "jonghoon.lee@email.com", "1234566");
        assertThrows(PersistenceException.class, () -> {
            repository.save(user);
        });
    }
}
