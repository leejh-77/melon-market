package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.user.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepositoryExtension {

    List<Post> findLikedPosts(int count, long userId);

}
