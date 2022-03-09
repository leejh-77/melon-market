package app.melon.web.controllers;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.User;
import app.melon.domain.services.PostService;
import app.melon.domain.services.UserService;
import app.melon.web.configs.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SecurityConfiguration.class, PostController.class})
public class PostControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postServiceMock;

    @MockBean
    private UserService userServiceMock;

    @Test
    public void getPost_shouldSuccess() throws Exception {
        LocalDateTime time = LocalDateTime.of(2000, 10, 1, 0, 0);

        User user = new User("jonghoon@email.com", "Jonghoon Lee", "111111");

        Post post = new Post();
        post.setTitle("Title");
        post.setBody("Body");
        post.setPrice(12000);
        post.setUser(user);
        post.setCreatedTime(time);
        post.setViewCount(0);

        PostImage image = new PostImage();
        image.setPost(post);
        image.setImageUrl("image");
        image.setCreatedTime(LocalDateTime.now());

        doReturn(user).when(userServiceMock).getUser(anyLong());
        doReturn(post).when(postServiceMock).findPost(anyLong());

        String expected = "{\"user\":{\"id\":0,\"imageUrl\":null,\"username\":\"Jonghoon Lee\"},\"post\":{\"id\":0,\"title\":\"Title\",\"body\":\"Body\",\"price\":12000,\"likedByMe\":false,\"likeCount\":0,\"chatCount\":0,\"viewCount\":0,\"imageUrls\":[\"image\"],\"createdTime\":\"2000-10-01T00:00:00\"}}";

        mvc.perform(get("/api/posts/1"))
                .andExpect(status().is(200))
                .andExpect(content().string(expected));
    }
}
