package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.web.requests.RegisterRequest;
import app.melon.web.results.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import app.melon.domain.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws ApiException {
        service.registerUser(request.toCommand());
        return ApiResult.created();
    }
}
