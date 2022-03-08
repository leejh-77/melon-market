package app.melon.domain.services;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.ImageStorage;
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
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ImageStorage imageStorage;
    private final UserService userService;

    public PostService(PostRepository postRepository,
                       PostImageRepository postImageRepository,
                       @Qualifier("PostImageStorage") ImageStorage imageStorage,
                       UserService userService) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.imageStorage = imageStorage;
        this.userService = userService;
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
        Post post = new Post(command.getTitle(),
                command.getBody(), command.getPrice(),
                simpleUser.getUserId(), LocalDateTime.now());

        this.postRepository.save(post);

        for (MultipartFile file : files) {
            String filename = imageStorage.saveImage(file);
            this.postImageRepository.save(new PostImage(post.getId(), filename));
        }
    }

    public List<Post> getPosts() {
        return this.postRepository.findTopPosts(30);
    }

    public PostImage findCoverImage(long id) {
        return this.postImageRepository.findImageByPostId(id);
    }

    public byte[] getImage(String url) throws ApiException {
        byte[] image = this.imageStorage.loadImage(url);
        if (image == null) {
            throw ApiException.of(Errors.ImageNotFound);
        }
        return image;
    }

    public Post getPost(long postId) {
        return this.postRepository.findById(postId);
    }

    public User getUserByPost(Post post) {
        return this.userService.getUser(post.getUserId());
    }

    public List<PostImage> getPostImages(long postId) {
        return this.postImageRepository.findImagesByPostId(postId);
    }
}
