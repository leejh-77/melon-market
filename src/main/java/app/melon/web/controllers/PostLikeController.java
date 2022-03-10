package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.services.PostService;
import app.melon.web.results.ApiResult;
import app.melon.web.security.AuthenticationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/posts/{postId}/likes")
public class PostLikeController {

    private final PostService postService;

    public PostLikeController(PostService postService) {
        this.postService = postService;
    }

    @Secured(value = {"ROLE_USER"})
    @PostMapping
    public ResponseEntity<?> likePost(@PathVariable long postId) throws ApiException {
        SimpleUser user = AuthenticationUtils.peekSimpleUser();
        if (user == null) {
            return ApiResult.unauthorized();
        }
        this.postService.likePost(postId, user.getUserId());
        return ApiResult.ok();
    }

    @Secured(value = {"ROLE_USER"})
    @DeleteMapping
    public ResponseEntity<?> dislikePost(@PathVariable long postId) {
        SimpleUser user = AuthenticationUtils.peekSimpleUser();
        if (user == null) {
            return ApiResult.unauthorized();
        }
        this.postService.dislikePost(postId, user.getUserId());
        return ApiResult.ok();
    }

}
