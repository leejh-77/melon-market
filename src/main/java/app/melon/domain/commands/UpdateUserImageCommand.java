package app.melon.domain.commands;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateUserImageCommand {

    private final long userId;
    private final MultipartFile file;

    public UpdateUserImageCommand(long userId, MultipartFile file) {
        this.userId = userId;
        this.file = file;
    }
}
