package app.melon.domain.services;

import app.melon.domain.commands.RegisterCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceTests {

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserService userService;

    @Test
    public void registerUser_shouldSuccess() throws ApiException {
        doReturn(null).when(userRepositoryMock).findByEmailAddress(any());

        RegisterCommand command = new RegisterCommand(
                "jonghoon.lee",
                "jonghoon.lee@email.com",
                "12345566"
        );

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        userService.registerUser(command);

        verify(userRepositoryMock).save(captor.capture());
        User user = captor.getValue();

        assertEquals(command.getEmailAddress(), user.getEmailAddress());
        assertEquals(command.getUsername(), user.getUsername());

        // password 는 encrypt 되어야 하므로 같지 않다.
        assertNotEquals(command.getPassword(), user.getPassword());
    }

}
