package app.melon.web.results;

import app.melon.domain.models.user.User;
import lombok.Getter;

@Getter
public class GetMeResult {
    private String username;
    private String imagePath;

    public static GetMeResult from(User user) {
        GetMeResult result = new GetMeResult();
        result.username = user.getUsername();
        result.imagePath = user.getImagePath();
        return result;
    }
}
