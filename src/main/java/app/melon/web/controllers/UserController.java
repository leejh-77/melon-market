package app.melon.web.controllers;

import app.melon.domain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping("/me/images")
    public ResponseEntity<?> updateImage(MultipartFile file, HttpServletRequest request)
    {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
//        service.updateUserImage();
        return null;
    }
}
