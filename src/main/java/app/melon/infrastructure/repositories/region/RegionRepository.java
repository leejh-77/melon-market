package app.melon.infrastructure.repositories.region;

import app.melon.domain.models.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {
    List<Region> findByCodeLike(String code);
}
