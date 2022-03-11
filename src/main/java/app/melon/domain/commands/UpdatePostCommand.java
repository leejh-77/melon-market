package app.melon.domain.commands;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UpdatePostCommand {

    private final long postId;
    private final String title;
    private final String body;
    private final int price;
    private final List<Long> deletedImages;
    private final List<MultipartFile> addedImages;

    public UpdatePostCommand(long postId, String title, String body, List<Long> deletedImages, int price, List<MultipartFile> addedImages) {
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.deletedImages = deletedImages;
        this.price = price;
        this.addedImages = addedImages;
    }

}
