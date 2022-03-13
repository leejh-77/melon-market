package app.melon.web.controllers;

import app.melon.domain.commands.UpdateUserCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.services.UserService;
import app.melon.infrastructure.utils.JwtManager;
import app.melon.web.requests.UpdateUserRequest;
import app.melon.web.results.ApiResult;
import app.melon.web.results.GetMeResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> getMe(@AuthenticationPrincipal SimpleUser user) {
        String jwt = this.jwtManager.generate(user.getUserId());
        return GetMeResult.from(user.getUser(), jwt);
    }

    @Secured("ROLE_USER")
    @PutMapping("/me/images")
    public ResponseEntity<?> updateImage(UpdateUserRequest request,
                                         @AuthenticationPrincipal SimpleUser user) throws ApiException {
        this.service.updateUserImage(request.toCommand(user.getUserId()));
        return ApiResult.ok();
    }
}
