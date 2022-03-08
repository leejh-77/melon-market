package app.melon.web.security;

import app.melon.domain.models.user.SimpleUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class AuthenticationUtils {

    public static SimpleUser extractSimpleUserFromPrincipal(Principal principal) {
        if (!(principal instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        if (!(token.getPrincipal() instanceof SimpleUser)) {
            return null;
        }
        return (SimpleUser) token.getPrincipal();
    }
}
