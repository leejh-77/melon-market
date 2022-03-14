package app.melon.domain.commands;

import app.melon.domain.models.user.User;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateUserCommand {

    private final User user;
    private final String username;
    private final MultipartFile file;

    public UpdateUserCommand(User user, String username, MultipartFile file) {
        this.user = user;
        this.username = username;
        this.file = file;
    }
}
