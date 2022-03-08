package app.melon.web.controllers;

import app.melon.domain.commands.UpdateUserImageCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.domain.services.UserService;
import app.melon.web.results.ApiResult;
import app.melon.web.results.GetMeResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof SimpleUser)) {
            return ApiResult.failure("Failed to get user");
        }
        SimpleUser simpleUser = (SimpleUser) principal;
        User user = this.service.getUser(simpleUser.getUserId());
        return GetMeResult.from(user);
    }

    @PutMapping("/me/images")
    public ResponseEntity<?> updateImage(MultipartFile file) throws ApiException {
        SimpleUser user = (SimpleUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.service.updateUserImage(new UpdateUserImageCommand(user.getUserId(), file));
        return ApiResult.ok();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleException(ApiException e) {
        return ApiResult.failure(e.getErrorMessage());
    }
}
