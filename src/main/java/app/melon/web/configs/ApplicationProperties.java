package app.melon.web.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${app.path.user-images}")
    private String userImagesPath;

    @Value("${app.path.post-images}")
    private String postImagesPath;

    public String getUserImagesPath() {
        return userImagesPath;
    }

    public String getPostImagesPath() {
        return postImagesPath;
    }
}
