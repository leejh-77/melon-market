package app.melon.infrastructure.repostories.post;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.User;
import app.melon.helper.DataCreator;
import app.melon.infrastructure.repositories.post.PostImageRepository;
import app.melon.infrastructure.repositories.post.PostRepository;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PostImageRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void createPostImage_shouldSuccess() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        Post post = DataCreator.newPost(user);
        this.postRepository.save(post);

        for (int i = 0; i < 5; i++) {
            PostImage image = DataCreator.newPostImage(post);
            this.postImageRepository.save(image);
        }

        List<PostImage> images = post.getImages();
        assertEquals(5, images.size());
    }

    @Test
    public void fetchJoin() {
        User user = DataCreator.newUser();
        this.userRepository.save(user);

        for (int i = 0; i < 5; i++) {
            Post post = DataCreator.newPost(user);
            this.postRepository.save(post);

            for (int j = 0; j < 5; j++) {
                PostImage image = DataCreator.newPostImage(post);
                this.postImageRepository.save(image);
            }
        }

        this.em.flush();
        this.em.clear();

        System.out.println("#### without jetch join ###");

        List<Post> posts = this.postRepository.findAllPosts();
        posts.get(0).getImages().get(0).getImageUrl();
//        for (Post post : posts) {
//            for (PostImage image : post.getImages()) {
//                System.out.println(image.getImageUrl());
//            }
//        }

        System.out.println("### with fetch join ###");

        posts = this.postRepository.findAllFetchJoin();
        for (Post p : posts) {
            List<PostImage> images = p.getImages();
            for (PostImage image : images) {
                image.getImageUrl();
            }
        }
    }

}
