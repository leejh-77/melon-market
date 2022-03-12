package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryExtension {

    @Query("select p from Post p join fetch p.images")
    List<Post> findAllFetchJoin();

    @Query("select p from Post p")
    List<Post> findAllPosts();

}
