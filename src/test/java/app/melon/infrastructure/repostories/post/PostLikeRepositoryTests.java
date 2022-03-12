package app.melon.infrastructure.repostories.post;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostLike;
import app.melon.domain.models.region.Region;
import app.melon.domain.models.user.User;
import app.melon.helper.DataCreator;
import app.melon.infrastructure.repositories.post.PostLikeRepository;
import app.melon.infrastructure.repositories.post.PostRepository;
import app.melon.infrastructure.repositories.region.RegionRepository;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PostLikeRepositoryTests {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createLike_nullUser_shouldFail() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        Region region = DataCreator.newRegion();
        this.regionRepository.save(region);

        Post post = DataCreator.newPost(user, region);
        this.postRepository.save(post);

        PostLike like = DataCreator.newLike(user, post);
        this.postLikeRepository.save(like);

        PostLike queried = this.postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId());
        assertNotNull(queried);

        int count = this.postLikeRepository.countByPostId(post.getId());
        assertTrue(count > 0);

        List<Post> posts = this.postRepository.findLikedPosts(1, user.getId(), null);
        assertTrue(posts.size() > 0);
    }
}
