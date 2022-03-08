package app.melon.web.results;

import app.melon.domain.models.user.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class GetMeResult {
    private String username;
    private String imagePath;

    public static ResponseEntity<GetMeResult> from(User user) {
        GetMeResult result = new GetMeResult();
        result.username = user.getUsername();
        result.imagePath = user.getImagePath();
        return ApiResult.ok(result);
    }
}
