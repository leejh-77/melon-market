package app.melon.web.results;

import app.melon.domain.models.user.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class MeResult {
    private long id;
    private String username;
    private String imageUrl;
    private String sockToken;

    public static ResponseEntity<MeResult> from(User user, String sockToken) {
        MeResult result = new MeResult();
        result.id = user.getId();
        result.username = user.getUsername();
        result.imageUrl = user.getImageUrl();
        result.sockToken = sockToken;
        return ApiResult.ok(result);
    }
}
