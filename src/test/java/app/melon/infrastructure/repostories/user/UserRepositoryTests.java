package app.melon.infrastructure.repostories.user;

import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Test
    public void nullUsername_shouldFail() {
        User user = User.create("jonghoon.lee@email.com", null, "1234566");
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(user);
        });
    }

    @Test
    public void nullEmailAddress_shouldFail() {
        User user = User.create(null, "jonghoon", "1234566");
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(user);
        });
    }

    @Test
    public void duplicateEmailAddress_shouldFail() {
        String emailAddress = "jonghoon.lee@email.com";
        User user = User.create(emailAddress, "jonghoon", "1234566");
        repository.save(user);

        User newUser = User.create(emailAddress, "newUser", "123455666");
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(newUser);
        });
    }

    @Test
    public void validRequest_shouldSuccess() {
        String emailAddress = "jonghoon.lee@email.com";
        String username = "jonghoon";
        String password = "12345678";

        User user = User.create(emailAddress, username, password);
        repository.save(user);

        user = repository.findByEmailAddress(emailAddress);

        assertEquals(username, user.getUsername());
        assertEquals(emailAddress, user.getEmailAddress());
    }
}
