package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.domain.services.UserService;
import app.melon.infrastructure.utils.JwtManager;
import app.melon.web.requests.UpdateUserRequest;
import app.melon.web.results.ApiResult;
import app.melon.web.results.MeResult;
import app.melon.web.results.UserResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final JwtManager jwtManager;

    public UserController(UserService service,
                          JwtManager jwtManager) {
        this.service = service;
        this.jwtManager = jwtManager;
    }

    @Secured("ROLE_USER")
    @GetMapping("/me")
    public ResponseEntity<MeResult> getMe(@AuthenticationPrincipal SimpleUser user) {
        String jwt = this.jwtManager.generate(user.getUserId());
        return MeResult.from(user.getUser(), jwt);
    }

    @Secured("ROLE_USER")
    @PutMapping("/me/images")
    public ResponseEntity<?> updateImage(UpdateUserRequest request,
                                         @AuthenticationPrincipal SimpleUser user) throws ApiException {
        this.service.updateUserImage(request.toCommand(user.getUserId()));
        return ApiResult.ok();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResult> getUser(@PathVariable(name = "userId") long userId) {
        User user = this.service.getUser(userId);
        UserResult result = UserResult.from(user);
        return ApiResult.ok(result);
    }
}
