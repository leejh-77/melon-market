package app.melon.web.controllers;

import app.melon.domain.models.region.Region;
import app.melon.domain.services.RegionService;
import app.melon.web.results.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<?> getRegions(@RequestParam(value = "code", required = false) String code) {
        List<Region> regions = this.regionService.findByCode(code);
        return ApiResult.ok(regions);
    }
}
