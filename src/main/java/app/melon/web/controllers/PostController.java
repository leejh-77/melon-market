package app.melon.web.controllers;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.User;
import app.melon.domain.services.PostService;
import app.melon.domain.services.UserService;
import app.melon.web.results.ApiResult;
import app.melon.web.results.PostDetailResult;
import app.melon.web.results.PostResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getPostList() {
        List<Post> posts = this.postService.findPostList();
        List<PostResult> results = new ArrayList<>();
        for (Post post : posts) {
            PostImage image = this.postService.findCoverImage(post.getId());
            int likeCount = this.postService.findLikeCount(post.getId());
            results.add(PostResult.from(post, image, likeCount));
        }
        return ApiResult.ok(results);
    }

    @GetMapping("/images/{imageUrl}")
    public ResponseEntity<?> getImage(@PathVariable("imageUrl") String url) throws ApiException {
        byte[] image = this.postService.getImage(url);
        return ApiResult.ok(image);
    }

    @PostMapping
    @Secured(value = {"ROLE_USER"})
    public ResponseEntity<?> addPost(MultipartHttpServletRequest request) throws ApiException {
        AddPostCommand command = new AddPostCommand(
                request.getParameter("title"),
                request.getParameter("body"),
                Integer.parseInt(request.getParameter("price")),
                request.getFiles("images")
        );

        this.postService.addPost(command);
        return ApiResult.created();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable long postId) {
        Post post = this.postService.findPost(postId);
        User user = this.userService.getUser(post.getUserId());
        List<PostImage> images = this.postService.findPostImages(postId);
        return PostDetailResult.from(user, post, images);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable long postId) {
        return null;
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable long postId) {
        return null;
    }

    @Secured(value = {"ROLE_USER"})
    @PostMapping("/{postId}/likes")
    public ResponseEntity<?> likePost(@PathVariable long postId, Principal principal) {
        this.postService.likePost(postId, principal);
        return ApiResult.ok();
    }

    @Secured(value = {"ROLE_USER"})
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<?> dislikePost(@PathVariable long postId, Principal principal) {
        this.postService.dislikePost(postId, principal);
        return ApiResult.ok();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleException(ApiException e) {
        return ApiResult.failure(e.getErrorMessage());
    }
}
