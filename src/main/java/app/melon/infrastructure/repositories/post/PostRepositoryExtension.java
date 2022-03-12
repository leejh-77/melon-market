package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.Post;

import java.util.List;

public interface PostRepositoryExtension {

    List<Post> findLikedPosts(int count, long userId, String region);

    List<Post> findRecentPosts(int count, String query, String region);
}
