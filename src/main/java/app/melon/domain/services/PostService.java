package app.melon.domain.services;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.ImageStorage;
import app.melon.domain.models.like.Like;
import app.melon.domain.models.like.LikeRepository;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.post.PostImageRepository;
import app.melon.domain.models.post.PostRepository;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ImageStorage imageStorage;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository,
                       PostImageRepository postImageRepository,
                       LikeRepository likeRepository,
                       @Qualifier("PostImageStorage") ImageStorage imageStorage) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.likeRepository = likeRepository;
        this.imageStorage = imageStorage;
    }

    public void addPost(AddPostCommand command) throws ApiException {
        List<MultipartFile> files = command.getImages();
        if (files.size() == 0) {
            throw ApiException.of(Errors.ImageNotFound);
        }
        if (!StringUtils.hasText(command.getTitle())) {
            throw ApiException.of(Errors.InvalidRequest);
        }

        SimpleUser simpleUser = (SimpleUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Post post = Post.builder()
                .title(command.getTitle())
                .body(command.getBody())
                .price(command.getPrice())
                .userId(simpleUser.getUserId())
                .createdTime(LocalDateTime.now()).build();

        this.postRepository.save(post);

        for (MultipartFile file : files) {
            String filename = imageStorage.saveImage(file);
            this.postImageRepository.save(new PostImage(post.getId(), filename));
        }
    }

    public List<Post> findPostList() {
        return this.postRepository.findTopPosts(30);
    }

    public PostImage findCoverImage(long id) {
        return this.postImageRepository.findImageByPostId(id);
    }

    public int findLikeCount(long id) {
        return this.likeRepository.findCountByPostId(id);
    }

    public byte[] getImage(String url) throws ApiException {
        byte[] image = this.imageStorage.loadImage(url);
        if (image == null) {
            throw ApiException.of(Errors.ImageNotFound);
        }
        return image;
    }

    public Post findPost(long postId) {
        return this.postRepository.findById(postId);
    }

    public List<PostImage> findPostImages(long postId) {
        return this.postImageRepository.findImagesByPostId(postId);
    }

    public void likePost(long postId, Principal principal) {
        SimpleUser user = (SimpleUser) principal;
        Like like = this.likeRepository.findByUserIdAndPostId(user.getUserId(), postId);
        if (like != null) {
            return;
        }
        like = Like.builder()
                .userId(user.getUserId())
                .postId(postId).build();
        this.likeRepository.save(like);
    }

    public void dislikePost(long postId, Principal principal) {
        SimpleUser user = (SimpleUser) principal;
        Like like = this.likeRepository.findByUserIdAndPostId(user.getUserId(), postId);
        if (like == null) {
            return;
        }
        this.likeRepository.delete(like);
    }
}
