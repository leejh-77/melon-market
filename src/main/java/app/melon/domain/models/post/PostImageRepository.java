package app.melon.domain.models.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
