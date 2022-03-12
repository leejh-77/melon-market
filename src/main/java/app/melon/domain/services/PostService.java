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
import app.melon.domain.models.post.PostViewManagement;
import app.melon.domain.models.region.Region;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.post.PostLikeRepository;
import app.melon.infrastructure.repositories.post.PostRepository;
import app.melon.infrastructure.repositories.region.RegionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private static final int LIST_QUERY_COUNT = 30;

    private final PostViewManagement postViewManagement;

    private final PostRepository postRepository;
    private final PostLikeRepository likeRepository;
    private final ImageStorage imageStorage;
    private final RegionRepository regionRepository;

    public PostService(PostRepository postRepository,
                       PostLikeRepository likeRepository,
                       RegionRepository regionRepository,
                       PostViewManagement postViewManagement,
                       @Qualifier("PostImageStorage") ImageStorage imageStorage) {
        this.postRepository = postRepository;
        this.regionRepository = regionRepository;
        this.likeRepository = likeRepository;
        this.imageStorage = imageStorage;
        this.postViewManagement = postViewManagement;
    }

    public List<Post> getPostList(PostListType type, String query, String region) throws ApiException {
        if (type == PostListType.Recent) {
            return this.postRepository.findRecentPosts(LIST_QUERY_COUNT, query, region);
        }
        else if (type == PostListType.Like) {
            SimpleUser user = SimpleUser.peek();
            if (user == null) {
                throw ApiException.of(Errors.InvalidRequest);
            }
            return this.postRepository.findLikedPosts(LIST_QUERY_COUNT, user.getUserId(), region);
        }
        else if (type == PostListType.Me) {
            SimpleUser user = SimpleUser.peek();
            if (user == null) {
                throw ApiException.of(Errors.InvalidRequest);
            }
            return this.postRepository.findMyPosts(LIST_QUERY_COUNT, user.getUserId());
        }
        else if (type == PostListType.Popular) {
            List<Long> list = this.postViewManagement.getViewRankList(10);
            return this.postRepository.findAllById(list);
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

    public Post findPostAndManageView(long postId) throws ApiException {
        Optional<Post> opPost = this.postRepository.findById(postId);
        if (opPost.isEmpty()) {
            throw ApiException.of(Errors.ItemNotFound);
        }
        Post post = opPost.get();
        this.postViewManagement.handle(post);
        return post;
    }

    public Post addPost(AddPostCommand command, User user) throws ApiException {
        Optional<Region> region = this.regionRepository.findById(command.getRegion());
        if (region.isEmpty()) {
            throw ApiException.of(Errors.InvalidRequest);
        }

        Post post = Post.create(
                command.getTitle(),
                command.getBody(),
                command.getPrice(),
                LocalDateTime.now(),
                region.get(),
                user
        );
        this.postRepository.save(post);
        this.saveAndAddImages(post, command.getImages());
        return post;
    }

    public void updatePost(UpdatePostCommand command, User user) throws ApiException {
        Post post = this.checkItemValidity(command.getPostId(), user);

        post.setTitle(command.getTitle());
        post.setPrice(command.getPrice());
        post.setBody(command.getBody());

        List<String> imagesToDelete = command.getDeletedImages();
        List<PostImage> deletes = post.getImages().stream().filter(image -> imagesToDelete.contains(image.getImageUrl()))
                .collect(Collectors.toList());

        this.saveAndAddImages(post, command.getAddedImages());
        this.deleteImages(deletes);

        this.postRepository.save(post);
    }

    public void deletePost(long postId, User user) throws ApiException {
        Post post = this.checkItemValidity(postId, user);
        this.deleteImages(post.getImages());
        this.postRepository.delete(post);
    }

    private void saveAndAddImages(Post post, List<MultipartFile> files) {
        files.forEach(file -> {
            String filename = imageStorage.saveImage(file);
            PostImage.create(post, filename, LocalDateTime.now().withNano(0));
        });
    }

    private void deleteImages(List<PostImage> images) {
        for (int i = images.size()-1; i >= 0; i--) {
            PostImage image = images.get(i);
            images.remove(i);
            this.imageStorage.deleteImage(image.getImageUrl());
        }
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
