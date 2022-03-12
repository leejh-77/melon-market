package app.melon.infrastructure.repositories.region;

import app.melon.domain.models.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {

    @Query("SELECT r FROM Region r WHERE r.code LIKE '%00000000'")
    List<Region> findCounties();

    @Query("SELECT r FROM Region r WHERE r.code LIKE :county AND LENGTH(r.district) = 0 AND LENGTH(r.town) > 0")
    List<Region> findTowns(@Param("county") String county);

    @Query("SELECT r FROM Region r WHERE r.code LIKE :town AND LENGTH(r.district) > 0")
    List<Region> findDistricts(@Param("town") String town);
}
