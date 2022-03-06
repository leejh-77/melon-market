package app.melon.domain.models.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {

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

    @Test
    public void nullEmailAddress_shouldFail() {
        User user = new User("jonghoon", null, "1234566");
        assertThrows(PersistenceException.class, () -> {
            repository.save(user);
        });
    }

    @Test
    public void duplicateEmailAddress_shouldFail() {
        String emailAddress = "jonghoon.lee@email.com";
        User user = new User("jonghoon", emailAddress, "1234566");
        repository.save(user);

        User newUser = new User("newUser", emailAddress, "123455666");
        assertThrows(PersistenceException.class, () -> {
            repository.save(newUser);
        });
    }

    @Test
    public void validRequest_shouldSuccess() {
        String emailAddress = "jonghoon.lee@email.com";
        String username = "jonghoon";
        String password = "12345678";

        User user = new User(username, emailAddress, password);
        repository.save(user);

        user = repository.findByEmailAddress(emailAddress);

        assertEquals(username, user.getUsername());
        assertEquals(emailAddress, user.getEmailAddress());
    }
}
