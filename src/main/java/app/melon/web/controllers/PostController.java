package app.melon.web.controllers;

import app.melon.domain.commands.PostListType;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.post.Post;
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
    public ResponseEntity<List<PostListResult>> getPostList(@RequestParam(name = "type") String type,
                                         @RequestParam(name = "query", required = false) String query,
                                         @RequestParam(name = "region", required = false) String region) throws ApiException {
        List<Post> posts = this.postService.getPostList(PostListType.fromName(type), query, region);
        List<PostListResult> results = new ArrayList<>();
        for (Post post : posts) {
            int likeCount = this.postService.findLikeCount(post.getId());
            results.add(PostListResult.from(post, likeCount));
        }
        return ApiResult.ok(results);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResult> getPost(@PathVariable long postId,
                                     @AuthenticationPrincipal SimpleUser user) throws ApiException {
        Post post = this.postService.findPostAndManageView(postId);
        int likeCount = this.postService.findLikeCount(postId);
        boolean likedByMe = user != null && this.postService.isLikedPost(postId, user.getUserId());
        PostDetailResult ret = PostDetailResult.from(post, likeCount, likedByMe);
        return ApiResult.ok(ret);
    }

    @GetMapping("/images/{imageUrl}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageUrl") String url) throws ApiException {
        byte[] image = this.postService.getImage(url);
        return ApiResult.ok(image);
    }

    @Secured(value = {"ROLE_USER"})
    @PostMapping
    public ResponseEntity<Long> addPost(@Valid AddPostRequest request,
                                     @AuthenticationPrincipal SimpleUser user) throws ApiException {
        Post post = this.postService.addPost(request.toCommand(), user.getUser());
        return ApiResult.ok(post.getId());
    }

    @Secured(value = {"ROLE_USER"})
    @PostMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable long postId,
                                        @Valid UpdatePostRequest request,
                                        @AuthenticationPrincipal SimpleUser user) throws ApiException {
        this.postService.updatePost(request.toCommand(postId), user.getUser());
        return ApiResult.ok();
    }

    @Secured(value = {"ROLE_USER"})
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable long postId,
                                        @AuthenticationPrincipal SimpleUser user) throws ApiException {
        this.postService.deletePost(postId, user.getUser());
        return ApiResult.ok();
    }
}
