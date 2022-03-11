package app.melon.web.controllers;

import app.melon.domain.commands.PostListType;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.services.PostService;
import app.melon.web.requests.AddPostRequest;
import app.melon.web.requests.UpdatePostRequest;
import app.melon.web.results.ApiResult;
import app.melon.web.results.PostDetailResult;
import app.melon.web.results.PostListResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getPostList(@RequestParam(name = "query") String query,
                                         @AuthenticationPrincipal SimpleUser user) {
        PostListType type = PostListType.fromName(query);
        if (type == PostListType.Like && user == null) {
            return ApiResult.badRequest();
        }
        List<Post> posts = this.postService.getPostList(PostListType.fromName(query), user.getUser());
        List<PostListResult> results = new ArrayList<>();
        for (Post post : posts) {
            PostImage image = post.getImages().get(0);
            int likeCount = this.postService.findLikeCount(post.getId());
            results.add(PostListResult.from(post, image, likeCount));
        }
        return ApiResult.ok(results);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable long postId,
                                     @AuthenticationPrincipal SimpleUser user) {
        Post post = this.postService.findPost(postId);
        int likeCount = this.postService.findLikeCount(postId);
        boolean likedByMe = user != null && this.postService.isLikedPost(postId, user.getUserId());
        return PostDetailResult.from(post, likeCount, likedByMe);
    }

    @GetMapping("/images/{imageUrl}")
    public ResponseEntity<?> getImage(@PathVariable("imageUrl") String url) throws ApiException {
        byte[] image = this.postService.getImage(url);
        return ApiResult.ok(image);
    }

    @PostMapping
    public ResponseEntity<?> addPost(@Valid AddPostRequest request,
                                     @AuthenticationPrincipal SimpleUser user) {
        this.postService.addPost(request.toCommand(), user.getUser());
        return ApiResult.created();
    }


    @Secured(value = {"ROLE_USER"})
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable long postId, UpdatePostRequest request) {
        return null;
    }

    @Secured(value = {"ROLE_USER"})
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable long postId,
                                        @AuthenticationPrincipal SimpleUser user) throws ApiException {
        this.postService.deletePost(postId, user.getUser());
        return ApiResult.ok();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleException(ApiException e) {
        return ApiResult.failure(e.getErrorMessage());
    }
}
