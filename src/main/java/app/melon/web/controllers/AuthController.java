package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.web.payloads.RegisterRequest;
import app.melon.web.results.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import app.melon.domain.services.UserService;

import javax.validation.Valid;

@Controller
public class AuthController {

    private UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/register")
    public ResponseEntity<ApiResult<?>> register(@RequestBody @Valid RegisterRequest request) {
        try {
            service.createUser(request);
            return ApiResult.created();
        } catch (ApiException e) {
            return ApiResult.failure(e.getErrorMessage());
        }
    }
}
