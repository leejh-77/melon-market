package app.melon.web.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${app.path.user-images}")
    private String userImagesPath;

    @Value("${app.path.post-images}")
    private String postImagesPath;

    @Value("${app.region.download_url}")
    private String regionDownloadUrl;

    public String getUserImagesPath() {
        return userImagesPath;
    }

    public String getPostImagesPath() {
        return postImagesPath;
    }

    public String getRegionDownloadUrl() {
        return regionDownloadUrl;
    }
}
