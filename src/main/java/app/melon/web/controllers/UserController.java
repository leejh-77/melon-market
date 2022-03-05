package app.melon.web.controllers;

import app.melon.domain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/:id")
    public ResponseEntity<?> getUser(@PathParam("id") long id) {
        return null;
    }

    @GetMapping("/:id/images")
    public ResponseEntity<?> getImage(@PathParam("id") long id) {
        return null;
    }

    @PutMapping("/:id/images")
    public ResponseEntity<?> updateImage(@PathParam("id") long id, MultipartFile file)
    {
        return null;
    }
}
