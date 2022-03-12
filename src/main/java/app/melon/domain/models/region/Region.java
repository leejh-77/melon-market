package app.melon.domain.models.region;

import javax.persistence.*;

@Entity
@Table(name = "region")
public class Region {

    @Id
    private String code;

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "town")
    private String town;

    @Column(name = "district")
    private String district;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
