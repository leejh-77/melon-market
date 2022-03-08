package app.melon.web.security;

import app.melon.domain.models.user.SimpleUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class AuthenticationUtils {

    public static SimpleUser peekSimpleUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SimpleUser) {
            return (SimpleUser) principal;
        }
        return null;
    }
}
