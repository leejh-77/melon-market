package app.melon.domain.models.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryExtension {

    List<Post> findTop30ByOrderByCreatedTimeDesc();
}
