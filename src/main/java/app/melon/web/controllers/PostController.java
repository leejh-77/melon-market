package app.melon.web.controllers;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.services.PostService;
import app.melon.web.results.ApiResult;
import app.melon.web.results.PostDetailResult;
import app.melon.web.results.PostResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getPosts() {
        List<Post> posts = this.service.getPosts();
        List<PostResult> results = new ArrayList<>();
        for (Post post : posts) {
            PostImage image = this.service.findCoverImage(post.getId());
            results.add(PostResult.from(post, image));
        }
        return ApiResult.ok(results).toResponse();
    }

    @GetMapping("/images/{imageUrl}")
    public ResponseEntity<?> getImage(@PathVariable("imageUrl") String url) throws ApiException {
        byte[] image = this.service.getImage(url);
        return ApiResult.ok(image).toResponse();
    }

    @PostMapping
    @Secured(value = { "ROLE_USER" })
    public ResponseEntity<?> addPost(MultipartHttpServletRequest request) throws ApiException {
        String title = request.getParameter("title");
        String body = request.getParameter("body");

        int price = Integer.parseInt(request.getParameter("price"));
        List<MultipartFile> files = request.getFiles("images");

        this.service.addPost(new AddPostCommand(title, body, price, files));
        return ApiResult.created().toResponse();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable long postId) {
        Post post = this.service.getPost(postId);
        List<PostImage> images = this.service.getPostImages(postId);
        return ApiResult.ok(PostDetailResult.from(post, images)).toResponse();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable long postId) {
        return null;
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable long postId) {
        return null;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleException(ApiException e) {
        return ApiResult.failure(e.getErrorMessage()).toResponse();
    }
}
