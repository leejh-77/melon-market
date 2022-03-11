package app.melon.domain.commands;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateUserCommand {

    private final long userId;
    private final String username;
    private final MultipartFile file;

    public UpdateUserCommand(long userId, String username, MultipartFile file) {
        this.userId = userId;
        this.username = username;
        this.file = file;
    }
}
