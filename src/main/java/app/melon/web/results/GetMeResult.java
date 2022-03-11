package app.melon.web.results;

import app.melon.domain.models.user.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class GetMeResult {
    private long id;
    private String username;
    private String imagePath;

    public static ResponseEntity<GetMeResult> from(User user) {
        GetMeResult result = new GetMeResult();
        result.id = user.getId();
        result.username = user.getUsername();
        result.imagePath = user.getImageUrl();
        return ApiResult.ok(result);
    }
}
