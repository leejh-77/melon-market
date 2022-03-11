package app.melon.domain.services;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.commands.PostListType;
import app.melon.domain.commands.UpdatePostCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.ImageStorage;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.post.PostLike;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.post.PostLikeRepository;
import app.melon.infrastructure.repositories.post.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Post> getPostList(PostListType type) throws ApiException {
        if (type == PostListType.Recent) {
            return this.postRepository.findTop30ByOrderByCreatedTimeDesc();
        }
        else if (type == PostListType.Popular) {
            throw new RuntimeException("Not implemented");
        }
        else if (type == PostListType.Like) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!(principal instanceof SimpleUser)) {
                throw ApiException.of(Errors.InvalidRequest);
            }
            SimpleUser user = (SimpleUser) principal;
            return this.postRepository.findLikedPosts(30, user.getUserId());
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

    public void addPost(AddPostCommand command, User user) {
        Post post = Post.create(
                command.getTitle(),
                command.getBody(),
                command.getPrice(),
                LocalDateTime.now(),
                user
        );
        this.postRepository.save(post);
        this.saveAndAddImages(post, command.getImages());
    }

    public void updatePost(UpdatePostCommand command, User user) throws ApiException {
        Post post = this.checkItemValidity(command.getPostId(), user);

        post.setTitle(command.getTitle());
        post.setPrice(command.getPrice());
        post.setBody(command.getBody());

        this.postRepository.save(post);

        List<Long> imagesToDelete = command.getDeletedImages();
        List<PostImage> deletes = post.getImages().stream().filter(image -> imagesToDelete.contains(image.getId()))
                .collect(Collectors.toList());

        this.saveAndAddImages(post, command.getAddedImages());
        this.deleteImages(deletes);
    }

    public void deletePost(long postId, User user) throws ApiException {
        Post post = this.checkItemValidity(postId, user);
        this.postRepository.delete(post);
        this.deleteImages(post.getImages());
    }

    private void saveAndAddImages(Post post, List<MultipartFile> files) {
        files.forEach(file -> {
            String filename = imageStorage.saveImage(file);
            PostImage.create(post, filename, LocalDateTime.now().withNano(0));
        });
    }

    private void deleteImages(List<PostImage> images) {
        images.forEach(image -> {
            image.getPost().getImages().remove(image);
            this.imageStorage.deleteImage(image.getImageUrl());
        });
    }

    private Post checkItemValidity(long postId, User user) throws ApiException {
        Optional<Post> opPost = this.postRepository.findById(postId);
        if (opPost.isEmpty()) {
            throw ApiException.of(Errors.ItemNotFound);
        }
        Post post = opPost.get();
        if (post.getUser().getId() != user.getId()) {
            throw ApiException.of(Errors.InvalidRequest);
        }
        return post;
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
}
