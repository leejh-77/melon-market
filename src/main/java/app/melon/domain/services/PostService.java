package app.melon.domain.services;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.commands.PostListType;
import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.ImageStorage;
import app.melon.domain.models.post.PostLike;
import app.melon.infrastructure.repositories.post.PostLikeRepository;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.infrastructure.repositories.post.PostRepository;
import app.melon.domain.models.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository likeRepository;
    private final ImageStorage imageStorage;

    public PostService(PostRepository postRepository,
                       PostLikeRepository likeRepository,
                       @Qualifier("PostImageStorage") ImageStorage imageStorage) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.imageStorage = imageStorage;
    }

    public void addPost(AddPostCommand command, User user) {
        Post post = Post.create(
                command.getTitle(),
                command.getBody(),
                command.getPrice(),
                LocalDateTime.now(),
                user
        );

        List<PostImage> images = post.getImages();
        for (MultipartFile file : command.getImages()) {
            String filename = imageStorage.saveImage(file);
            PostImage image = new PostImage();
            image.setPost(post);
            image.setImageUrl(filename);
            images.add(image);
        }
        this.postRepository.save(post);
    }

    public List<Post> getPostList(PostListType type, User user) {
        if (type == PostListType.Recent) {
            return this.postRepository.findTop30ByOrderByCreatedTimeDesc();
        }
        else if (type == PostListType.Popular) {
            throw new RuntimeException("Not implemented");
        }
        else if (type == PostListType.Like) {
            return this.postRepository.findLikedPosts(30, user.getId());
        }
        return List.of();
    }

    public int findLikeCount(long id) {
        return this.likeRepository.countByPostId(id);
    }

    public byte[] getImage(String url) throws ApiException {
        byte[] image = this.imageStorage.loadImage(url);
        if (image == null) {
            throw ApiException.of(Errors.ImageNotFound);
        }
        return image;
    }

    public Post findPost(long postId) {
        return this.postRepository.findById(postId).get();
    }

    public boolean isLikedPost(long postId, long userId) {
        return this.likeRepository.findByUserIdAndPostId(userId, postId) != null;
    }

    public void likePost(long postId, long userId) throws ApiException {
        PostLike like = this.likeRepository.findByUserIdAndPostId(userId, postId);
        if (like != null) {
            return;
        }
        Optional<Post> opPost = this.postRepository.findById(postId);
        if (opPost.isEmpty()) {
            throw ApiException.of(Errors.ItemNotFound);
        }
        Post post = opPost.get();
        like = PostLike.create(post, post.getUser());
        this.likeRepository.save(like);
    }

    public void dislikePost(long postId, long userId) {
        PostLike like = this.likeRepository.findByUserIdAndPostId(userId, postId);
        if (like == null) {
            return;
        }
        this.likeRepository.delete(like);
    }

    public void deletePost(long postId, User user) throws ApiException {
        Optional<Post> opPost = this.postRepository.findById(postId);
        if (opPost.isEmpty()) {
            throw ApiException.of(Errors.ItemNotFound);
        }
        Post post = opPost.get();
        if (post.getUser().getId() != user.getId()) {
            throw ApiException.of(Errors.InvalidRequest);
        }
        this.postRepository.delete(post);
    }
}
