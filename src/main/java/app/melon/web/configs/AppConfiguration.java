package app.melon.web.configs;

import app.melon.domain.files.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    private final ApplicationProperties applicationProperties;

    public AppConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean(name = "UserImageStorage")
    public ImageStorage userImageStorage() {
        return new ImageStorage(System.getProperty("user.dir") + applicationProperties.getUserImagesPath());
    }

    @Bean(name = "PostImageStorage")
    public ImageStorage postImageStorage() {
        return new ImageStorage(System.getProperty("user.dir") + applicationProperties.getPostImagesPath());
    }
}
