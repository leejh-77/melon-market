package app.melon.web.requests;

import app.melon.domain.commands.UpdateUserCommand;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateUserRequest {

    private MultipartFile file;
    private String username;

    public UpdateUserCommand toCommand(long userId) {
        return new UpdateUserCommand(userId, this.username, this.file);
    }
}
