package app.melon.domain.files;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
public class UserImageStorageTests {

    @Autowired
    private ImageStorage storage;

    @Test
    public void saveImage() throws IOException {
        File image = new File("src/test/resources/dangdang.png");
        byte[] bytes = Files.readAllBytes(image.toPath());

        MockMultipartFile mock = new MockMultipartFile("image.png", bytes);
        String filename = storage.saveImage(mock);
        byte[] loaded = storage.loadImage(filename);

        assertArrayEquals(bytes, loaded);
    }

    @Test
    public void getPath() {
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
    }
}
