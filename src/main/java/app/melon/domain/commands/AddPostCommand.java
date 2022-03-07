package app.melon.domain.commands;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class AddPostCommand {

    private final String title;
    private final String body;
    private final int price;
    private final List<MultipartFile> images;

    public AddPostCommand(String title, String body, int price, List<MultipartFile> images) {
        this.title = title;
        this.price = price;
        this.body = body;
        this.images = images;
    }
}
