package app.melon.web.controllers;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.User;
import app.melon.domain.services.PostService;
import app.melon.web.configs.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
    private PostService serviceMock;

    @Test
    public void getPost_shouldSuccess() throws Exception {
        LocalDateTime time = LocalDateTime.of(2000, 10, 1, 0, 0);

        User user = new User("Jonghoon Lee", "jonghoon@email.com", "111111");
        Post post = new Post("Title", "Body", 12000, 0, time);
        PostImage image = new PostImage(0, "image");

        doReturn(user).when(serviceMock).getUserByPost(any());
        doReturn(post).when(serviceMock).getPost(anyLong());
        doReturn(List.of(image)).when(serviceMock).getPostImages(anyLong());

        String expected = "{\"user\":{\"id\":0,\"imageUrl\":null,\"username\":\"Jonghoon Lee\"},\"post\":{\"id\":0,\"title\":\"Title\",\"body\":\"Body\",\"price\":12000,\"likeCount\":0,\"chatCount\":0,\"imageUrls\":[\"image\"],\"createdTime\":\"2000-10-01T00:00:00\"}}";

        mvc.perform(get("/api/posts/1"))
                .andExpect(status().is(200))
                .andExpect(content().json(expected));
    }
}
