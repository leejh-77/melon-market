package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
