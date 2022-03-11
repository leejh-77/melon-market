package app.melon.web.controllers;

import app.melon.domain.commands.AddPostCommand;
import app.melon.domain.commands.PostListType;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.User;
import app.melon.domain.services.PostService;
import app.melon.domain.services.UserService;
import app.melon.helper.DataCreator;
import app.melon.web.configs.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void getPostList_shouldSuccess() throws Exception {
        User user = DataCreator.newUser();
        Post post = DataCreator.newPost(user);
        PostImage image = DataCreator.newPostImage(post);

        doReturn(List.of(post)).when(postServiceMock).getPostList(PostListType.Recent);

        mvc.perform(get("/api/posts?query=recent"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].price").value(post.getPrice()))
                .andExpect(jsonPath("$[0].likeCount").value(0))
                .andExpect(jsonPath("$[0].chatCount").value(0))
                .andExpect(jsonPath("$[0].imageUrl").value(image.getImageUrl()));
    }

    @Test
    public void getPost_shouldSuccess() throws Exception {
        User user = DataCreator.newUser();
        Post post = DataCreator.newPost(user);
        PostImage image = DataCreator.newPostImage(post);

        doReturn(user).when(userServiceMock).getUser(anyLong());
        doReturn(post).when(postServiceMock).findPost(anyLong());

        mvc.perform(get("/api/posts/1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.imageUrl").value(user.getImageUrl()))
                .andExpect(jsonPath("$.user.username").value(user.getUsername()))
                .andExpect(jsonPath("$.post.id").value(post.getId()))
                .andExpect(jsonPath("$.post.title").value(post.getTitle()))
                .andExpect(jsonPath("$.post.body").value(post.getBody()))
                .andExpect(jsonPath("$.post.price").value(post.getPrice()))
                .andExpect(jsonPath("$.post.viewCount").value(post.getViewCount()))
                .andExpect(jsonPath("$.post.likeCount").value(0))
                .andExpect(jsonPath("$.post.chatCount").value(0))
                .andExpect(jsonPath("$.post.likedByMe").value(false))
                .andExpect(jsonPath("$.post.createdTime").value(post.getCreatedTime().toString()))
                .andExpect(jsonPath("$.post.imageUrls[0]").value(image.getImageUrl()));
    }

    @Test
    public void getImage_shouldSuccess() throws Exception {
        byte[] image = {111, 12, 0, 23};
        String url = "image2333";

        doReturn(image).when(postServiceMock).getImage(url);

        mvc.perform(get("/api/posts/images/" + url))
                .andExpect(status().is(200))
                .andExpect(content().bytes(image));
    }

    @Test
    @WithMockUser
    public void addPost_shouldSuccess() throws Exception {
        Post post = DataCreator.newPost();
        MockPart image1 = new MockPart("images", new byte[]{1, 2, 3});
        MockPart image2 = new MockPart("images", new byte[]{4, 5, 6});
        MockPart image3 = new MockPart("images", new byte[]{7, 8, 9});

        MockPart[] images = {image1, image2, image3};

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/api/posts")
                .part(images)
                .param("title", post.getTitle())
                .param("body", post.getBody())
                .param("price", String.valueOf(post.getPrice()));

        ArgumentCaptor<AddPostCommand> captor = ArgumentCaptor.forClass(AddPostCommand.class);

        mvc.perform(request)
                .andExpect(status().is(201));

        verify(postServiceMock).addPost(captor.capture());

        AddPostCommand command = captor.getValue();
        assertEquals(post.getTitle(), command.getTitle());
        assertEquals(post.getBody(), command.getBody());
        assertEquals(post.getPrice(), command.getPrice());

        for (int i = 0; i < command.getImages().size(); i++) {
            MultipartFile file = command.getImages().get(i);
            assertArrayEquals(images[i].getInputStream().readAllBytes(), file.getBytes());
        }
    }

    @Test
    public void updatePost_shouldSuccess() {

    }

    @Test
    public void deletePost_shouldSuccess() {

    }
}
