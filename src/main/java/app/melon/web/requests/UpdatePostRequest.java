package app.melon.web.requests;

import app.melon.domain.commands.UpdatePostCommand;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class UpdatePostRequest {


    @Size(min = 1)
    private String title;
    private String body;
    private int price;
    private List<Long> deletedImages;
    private List<MultipartFile> addedImages;


    public UpdatePostCommand toCommand(long postId) {
        return new UpdatePostCommand(postId, title, body, deletedImages, price, addedImages);
    }
}
