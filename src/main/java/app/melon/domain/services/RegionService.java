package app.melon.domain.services;

import app.melon.domain.models.region.Region;
import app.melon.infrastructure.repositories.region.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> findByCode(String code) {
        if (code == null) {
            return this.regionRepository.findCounties();
        }
        code = code.substring(0, code.length() - 1); // 끝에 붙은 * 제거
        code += "%";
        if (code.length() == 3) {
            return this.regionRepository.findTowns(code);
        }
        return this.regionRepository.findDistricts(code);
    }
}
