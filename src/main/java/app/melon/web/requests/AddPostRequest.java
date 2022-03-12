package app.melon.web.requests;

import app.melon.domain.commands.AddPostCommand;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class AddPostRequest {

    @Size(min = 1)
    private final String title;
    private final String body;
    private final int price;

    @NotNull
    private final String region;

    @NotNull
    private final List<MultipartFile> images;

    public AddPostRequest(String title, String body, int price, List<MultipartFile> images, String region) {
        this.title = title;
        this.body = body;
        this.price = price;
        this.images = images;
        this.region = region;
    }

    public AddPostCommand toCommand() {
        return new AddPostCommand(this.title, this.body, this.price, this.images, this.region);
    }
}
