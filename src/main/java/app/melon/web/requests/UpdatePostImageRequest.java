package app.melon.web.requests;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UpdatePostImageRequest {

    private final List<String> deletedImages;
    private final List<MultipartFile> images;

    public UpdatePostImageRequest(List<String> deletedImages, List<MultipartFile> images) {
        this.deletedImages = deletedImages;
        this.images = images;
    }
}
