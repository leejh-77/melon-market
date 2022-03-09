package app.melon.web.configs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class ApplicationPropertiesTests {

    @Autowired
    private ApplicationProperties properties;

    @Test
    public void getProperty_shouldSuccess() {
        String path = this.properties.getPostImagesPath();

        assertNotNull(path);
    }
}
