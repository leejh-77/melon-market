package app.melon.web.controllers;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.commands.PostListType;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.domain.services.PostService;
import app.melon.web.results.ApiResult;
import app.melon.web.results.PostDetailResult;
import app.melon.web.results.PostResult;
import app.melon.web.security.AuthenticationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    public ResponseEntity<?> getPostList(@RequestParam(name = "query") String query) throws ApiException {
        List<Post> posts = this.postService.getPostList(PostListType.fromName(query));
        List<PostResult> results = new ArrayList<>();
        for (Post post : posts) {
            PostImage image = post.getImages().get(0);
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
        User user = post.getUser();
        List<PostImage> images = post.getImages();

        int likeCount = this.postService.findLikeCount(postId);
        boolean likedByMe = false;
        SimpleUser simpleUser = AuthenticationUtils.peekSimpleUser();
        if (simpleUser != null) {
            likedByMe = this.postService.isLikedPost(postId, simpleUser.getUserId());
        }
        return PostDetailResult.from(user, post, images, likeCount, likedByMe);
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
        return ApiResult.failure(e.getErrorMessage());
    }
}
