package app.melon.web.results;

import app.melon.domain.models.user.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class UserResult {

    private long id;
    private String username;
    private String imageUrl;

    public static UserResult from(User user) {
        UserResult result = new UserResult();
        result.id = user.getId();
        result.username = user.getUsername();
        result.imageUrl = user.getImageUrl();
        return result;
    }

}
