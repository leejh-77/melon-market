package app.melon.web.requests;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class UpdatePostRequest {

    @Size(min = 1)
    private String title;
    private String body;
    private List<Long> deletedImages;
    private int price;
    private List<MultipartFile> addedImages;
}
