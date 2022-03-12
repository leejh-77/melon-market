package app.melon.web.results;

import app.melon.domain.models.region.Region;
import lombok.Getter;

@Getter
public class RegionResult {

    private String code;
    private String county;
    private String town;
    private String district;

    public static RegionResult from(Region region) {
        RegionResult ret = new RegionResult();
        ret.code = region.getCode();
        ret.county = region.getCounty();
        ret.town = region.getTown();
        ret.district = region.getDistrict();
        return ret;
    }
}
