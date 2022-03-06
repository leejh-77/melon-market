package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.web.payloads.RegisterRequest;
import app.melon.web.results.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import app.melon.domain.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws ApiException {
        service.createUser(request.toCommand());
        return ApiResult.created().toResponse();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleException(ApiException e) {
        return ApiResult.failure(e.getErrorMessage()).toResponse();
    }
}
