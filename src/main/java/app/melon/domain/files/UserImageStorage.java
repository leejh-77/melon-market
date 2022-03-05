package app.melon.domain.files;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class UserImageStorage {

    private static final String PATH = "/data/user/images";

    public void saveImage(long userId, MultipartFile file) throws IOException {
        File dir = new File(PATH);
        if (!dir.exists()) {
            boolean t = dir.mkdir();
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return;
        }

        String ext = contentType.split("/")[1];
        ImageType type = ImageType.fromValue(ext);
        String filename = userId + "." + type.getValue();

        Files.write(Path.of(dir + "/" + filename), file.getBytes());
    }

    public byte[] loadImage(long userId) {
        return null;
    }
}
