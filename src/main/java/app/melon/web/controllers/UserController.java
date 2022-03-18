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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "UserController")
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final JwtManager jwtManager;

    public UserController(UserService service,
                          JwtManager jwtManager) {
        this.service = service;
        this.jwtManager = jwtManager;
    }

    @Operation(description = "내 정보 조회")
    @Secured("ROLE_USER")
    @GetMapping("/me")
    public ResponseEntity<MeResult> getMe(@AuthenticationPrincipal SimpleUser user) {
        String jwt = this.jwtManager.generate(user.getUserId());
        return MeResult.from(user.getUser(), jwt);
    }

    @Secured("ROLE_USER")
    @PostMapping("/me")
    public ResponseEntity<?> updateImage(UpdateUserRequest request,
                                         @AuthenticationPrincipal SimpleUser user) throws ApiException {
        this.service.updateUserImage(request.toCommand(user.getUser()));
        return ApiResult.ok();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResult> getUser(@PathVariable(name = "userId") long userId) {
        User user = this.service.getUser(userId);
        UserResult result = UserResult.from(user);
        return ApiResult.ok(result);
    }

    @GetMapping("/images/{imageUrl}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable(name = "imageUrl") String imageUrl) {
        byte[] image = this.service.getUserImage(imageUrl);
        return ApiResult.ok(image);
    }
}
