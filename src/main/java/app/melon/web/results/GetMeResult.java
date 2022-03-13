package app.melon.web.results;

import app.melon.domain.models.user.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class GetMeResult {
    private long id;
    private String username;
    private String imagePath;
    private String sockToken;

    public static ResponseEntity<GetMeResult> from(User user, String sockToken) {
        GetMeResult result = new GetMeResult();
        result.id = user.getId();
        result.username = user.getUsername();
        result.imagePath = user.getImageUrl();
        result.sockToken = sockToken;
        return ApiResult.ok(result);
    }
}
