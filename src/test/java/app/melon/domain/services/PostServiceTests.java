package app.melon.domain.services;

import app.melon.domain.commands.UpdatePostCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.user.User;
import app.melon.helper.DataCreator;
import app.melon.infrastructure.repositories.post.PostRepository;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class PostServiceTests {

    @MockBean
    private PostRepository postRepositoryMock;

    @Autowired
    private PostService postService;

    @Test
    public void updatePost_itemNotExist_shouldFail() {
        UpdatePostCommand command = new UpdatePostCommand(
                0, "Title", "Body", List.of(), 1000, List.of()
        );
        doReturn(Optional.empty()).when(postRepositoryMock).findById(0L);
        assertThrows(ApiException.class, () -> {
            postService.updatePost(command, null);
        });
    }

    @Test
    public void updatePost_userNotMatched_shouldFail() {
        UpdatePostCommand command = new UpdatePostCommand(
                0, "Title", "Body", List.of(), 1000, List.of()
        );

        User user = DataCreator.newUser();
        Post post = DataCreator.newPost(user);

        User otherUser = mock(User.class);
        doReturn(1L).when(otherUser).getId();

        doReturn(Optional.of(post)).when(postRepositoryMock).findById(0L);
        assertThrows(ApiException.class, () -> {
            postService.updatePost(command, otherUser);
        });
    }

    @Test
    public void deletePost_itemNotExist_shouldFail() {
        assertThrows(ApiException.class, () -> {
            postService.deletePost(0, null);
        });
    }

    @Test
    public void deletePost_userNotMatched_shouldFail() {
        User user = DataCreator.newUser();
        Post post = DataCreator.newPost(user);

        User otherUser = mock(User.class);
        doReturn(1L).when(otherUser).getId();

        doReturn(Optional.of(post)).when(postRepositoryMock).findById(0L);
        
        assertThrows(ApiException.class, () -> {
            postService.deletePost(0L, otherUser);
        });
    }

}
