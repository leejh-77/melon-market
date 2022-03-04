package app.melon.web.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterPayload {

    @Size(min = 2, max = 50, message = "Username must be between 2 and 50")
    @NotNull
    private String username;

    @Email(message = "Email address must be valid")
    @Size(max = 100, message = "Email address must not be more than 100")
    @NotNull
    private String emailAddress;

    @Size(min = 6, max = 30, message = "Password must be between 6 and 30")
    @NotNull
    private String password;
}
