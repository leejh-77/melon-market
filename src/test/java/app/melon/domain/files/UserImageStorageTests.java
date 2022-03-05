package app.melon.domain.files;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ContextConfiguration(classes = UserImageStorage.class)
public class UserImageStorageTests {

    @Autowired
    private UserImageStorage storage;

    @Test
    public void saveImage() throws IOException {
        File image = new File("src/test/resources/dangdang.png");
        byte[] bytes = Files.readAllBytes(image.toPath());

        MockMultipartFile mock = new MockMultipartFile("image", bytes);
        storage.saveImage(0, mock);
        byte[] loaded = storage.loadImage(0);

        assertEquals(bytes, loaded);
    }
}
