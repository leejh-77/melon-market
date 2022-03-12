package app.melon.infrastructure.repostories.post;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.region.Region;
import app.melon.domain.models.user.User;
import app.melon.helper.DataCreator;
import app.melon.infrastructure.repositories.post.PostRepository;
import app.melon.infrastructure.repositories.region.RegionRepository;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void createPost_nullTitle_shouldFail() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        Region region = DataCreator.newRegion();
        this.regionRepository.save(region);

        Post post = DataCreator.newPost(user, region);
        post.setTitle(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            this.postRepository.save(post);
        });
    }

    @Test
    public void createPost_nullUser_shouldFail() {
        Post post = DataCreator.newPost();

        assertThrows(DataIntegrityViolationException.class, () -> {
            this.postRepository.save(post);
        });
    }

    @Test
    public void createPost_validInput_shouldSuccess() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        Region region = DataCreator.newRegion();
        this.regionRepository.save(region);

        Post post = DataCreator.newPost(user, region);
        this.postRepository.save(post);

        Optional<User> newUser = this.userRepository.findById(user.getId());
        Optional<Post> newPost = this.postRepository.findById(post.getId());

        assertTrue(newUser.isPresent());
        assertTrue(newPost.isPresent());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updatePost_nullTitle_shouldFail() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        Region region = DataCreator.newRegion();
        this.regionRepository.save(region);

        Post post = DataCreator.newPost(user, region);
        this.postRepository.save(post);

        post.setTitle(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            this.postRepository.save(post);
        });
    }

    @Test
    public void updatePost_nullUser_shouldFail() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        Region region = DataCreator.newRegion();
        this.regionRepository.save(region);

        Post post = DataCreator.newPost(user, region);
        this.postRepository.save(post);

        post.setUser(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            this.postRepository.saveAndFlush(post);
        });
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updatePost_validInput_shouldSuccess() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        Region region = DataCreator.newRegion();
        this.regionRepository.save(region);

        Post post = DataCreator.newPost(user, region);
        this.postRepository.save(post);

        User newUser = DataCreator.newUser();
        this.userRepository.save(newUser);

        post.setTitle("New title");
        post.setUser(newUser);

        this.postRepository.save(post);
    }

}
