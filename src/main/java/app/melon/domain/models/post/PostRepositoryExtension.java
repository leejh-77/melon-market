package app.melon.domain.models.post;

import app.melon.domain.models.user.User;

import java.util.List;

public interface PostRepositoryExtension {

    List<Post> findLikedPosts(int count, User user);
}
