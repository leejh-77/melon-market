package app.melon.web.controllers;

import app.melon.domain.services.PostService;
import app.melon.web.results.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts() {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> addPost() {
        return null;
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable long postId) {
        return null;
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable long postId) {
        return null;
    }
}
