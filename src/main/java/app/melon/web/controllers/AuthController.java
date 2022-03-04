package app.melon.web.controllers;

import app.melon.web.payloads.RegisterPayload;
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
    public ResponseEntity<String> register(@RequestBody @Valid RegisterPayload payload) {
        return ResponseEntity.status(200).build();
    }
}