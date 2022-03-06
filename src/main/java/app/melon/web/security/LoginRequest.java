package app.melon.web.security;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class LoginRequest {

    private String username;
    private String password;

    boolean isValid() {
        return StringUtils.hasText(username) && StringUtils.hasText(password);
    }
}
