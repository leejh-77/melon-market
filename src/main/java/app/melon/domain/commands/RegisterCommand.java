package app.melon.domain.commands;

import lombok.Getter;

@Getter
public class RegisterCommand {

    private final String username;
    private final String emailAddress;
    private final String password;

    public RegisterCommand(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
