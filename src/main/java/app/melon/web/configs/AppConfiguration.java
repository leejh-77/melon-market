package app.melon.web.configs;

import app.melon.domain.files.ImageStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean(name = "UserImageStorage")
    public ImageStorage userImageStorage() {
        return new ImageStorage(System.getProperty("user.dir") + "/data/user/images");
    }

    @Bean(name = "PostImageStorage")
    public ImageStorage postImageStorage() {
        return new ImageStorage(System.getProperty("user.dir") + "/data/post/images");
    }
}
