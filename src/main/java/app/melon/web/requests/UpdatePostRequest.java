package app.melon.web.requests;

import app.melon.domain.commands.UpdatePostCommand;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class UpdatePostRequest {

    @Size(min = 1)
    private final String title;
    private final String body;
    private final Integer price;
    private final List<String> deletedImages;
    private final List<MultipartFile> images;

    public UpdatePostRequest(String title, String body, Integer price, List<String> deletedImages, List<MultipartFile> images) {
        this.title = title;
        this.body = body;
        this.price = price;
        this.deletedImages = deletedImages == null ? List.of() : deletedImages;
        this.images = images == null ? List.of() : images;
    }

    public UpdatePostCommand toCommand(long postId) {
        return new UpdatePostCommand(postId, title, body, price, deletedImages, images);
    }
}
