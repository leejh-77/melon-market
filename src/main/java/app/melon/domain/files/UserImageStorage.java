package app.melon.domain.files;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class UserImageStorage {

    private static final String DIRECTORY = System.getProperty("user.dir") + "/data/user/images";

    public String saveImage(MultipartFile file) {
        Path path = Paths.get(DIRECTORY).toAbsolutePath().normalize();
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create image storage", e);
        }
        String filename = this.makeFilename(file);
        Path location = path.resolve(filename);

        try {
            Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save multipart file to" + location, e);
        }
        return filename;
    }

    private String makeFilename(MultipartFile file) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString();
        String ext = FilenameUtils.getExtension(file.getName());
        return timestamp + "." + uuid + (StringUtils.hasText(ext) ? "." + ext : "");
    }

    public byte[] loadImage(String filename) {
        Path path = Paths.get(DIRECTORY).toAbsolutePath().normalize();
        Path location = path.resolve(filename);
        if (!location.toFile().exists()) {
            return null;
        }
        try {
            return Files.readAllBytes(location);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file", e);
        }
    }
}
