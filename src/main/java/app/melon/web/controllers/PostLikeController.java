package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.services.PostService;
import app.melon.web.results.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class PostLikeController {

    private final PostService postService;

    public PostLikeController(PostService postService) {
        this.postService = postService;
    }

    @Secured(value = {"ROLE_USER"})
    @PostMapping
    public ResponseEntity<?> likePost(@PathVariable long postId,
                                      @AuthenticationPrincipal SimpleUser user) throws ApiException {
        this.postService.likePost(postId, user.getUser());
        return ApiResult.ok();
    }

    @Secured(value = {"ROLE_USER"})
    @DeleteMapping
    public ResponseEntity<?> dislikePost(@PathVariable long postId,
                                         @AuthenticationPrincipal SimpleUser user) {
        this.postService.dislikePost(postId, user.getUser());
        return ApiResult.ok();
    }

}
