package app.melon.domain.services;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.commands.PostListType;
import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.ImageStorage;
import app.melon.domain.models.like.PostLike;
import app.melon.domain.models.like.PostLikeRepository;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.post.PostImageRepository;
import app.melon.domain.models.post.PostRepository;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.domain.models.user.UserRepository;
import app.melon.web.security.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository likeRepository;
    private final ImageStorage imageStorage;

    public PostService(UserRepository userRepository,
                       PostRepository postRepository,
                       PostImageRepository postImageRepository,
                       PostLikeRepository likeRepository,
                       @Qualifier("PostImageStorage") ImageStorage imageStorage) {
        this.userRepository = userRepository;
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

        SimpleUser simpleUser = AuthenticationUtils.peekSimpleUser();
        if (simpleUser == null) {
            throw ApiException.of(Errors.UserNotFound);
        }

        Post post = new Post();
        post.setTitle(command.getTitle());
        post.setBody(command.getBody());
        post.setPrice(command.getPrice());
        post.setUser(this.userRepository.findById(simpleUser.getUserId()).get());
        post.setCreatedTime(LocalDateTime.now());

        List<PostImage> images = post.getImages();
        for (MultipartFile file : files) {
            String filename = imageStorage.saveImage(file);
            PostImage image = new PostImage();
            image.setPost(post);
            image.setImageName(filename);
            images.add(image);
        }
        this.postRepository.save(post);
    }

    public List<Post> getPostList(PostListType type) throws ApiException {
        if (type == PostListType.Recent) {
            return this.postRepository.findTop30ByOrderByCreatedTimeDesc();
        }
        else if (type == PostListType.Popular) {
            throw new RuntimeException("Not implemented");
        }
        else if (type == PostListType.Like) {
            SimpleUser simpleUser = AuthenticationUtils.peekSimpleUser();
            if (simpleUser == null) {
                throw ApiException.of(Errors.UserNotFound);
            }
            User user = this.userRepository.findById(simpleUser.getUserId()).get();
            return this.postRepository.findLikedPosts(30, user);
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

    public void likePost(long postId, long userId) {
        PostLike like = this.likeRepository.findByUserIdAndPostId(userId, postId);
        if (like != null) {
            return;
        }

        Post post = this.postRepository.findById(postId).get();
        like = new PostLike();
        like.setPost(post);
        like.setUser(post.getUser());

        this.likeRepository.save(like);
    }

    public void dislikePost(long postId, long userId) {
        PostLike like = this.likeRepository.findByUserIdAndPostId(userId, postId);
        if (like == null) {
            return;
        }
        this.likeRepository.delete(like);
    }
}
