package app.melon.web.requests;

import app.melon.domain.commands.UpdateUserCommand;
import app.melon.domain.models.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateUserRequest {

    private MultipartFile image;
    private String username;

    public UpdateUserCommand toCommand(User user) {
        return new UpdateUserCommand(user, this.username, this.image);
    }
}
