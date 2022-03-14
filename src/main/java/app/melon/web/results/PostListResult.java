package app.melon.web.results;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.region.Region;
import app.melon.infrastructure.repositories.region.RegionRepository;
import lombok.Getter;

@Getter
public class PostListResult {

    private long id;
    private String title;
    private int price;
    private int likeCount;
    private int chatCount;
    private String region;
    private String imageUrl;

    public static PostListResult from(Post post, int likeCount, int chatCount) {
        PostListResult ret = new PostListResult();
        ret.id = post.getId();
        ret.price = post.getPrice();
        ret.title = post.getTitle();
        ret.imageUrl = post.getImages().get(0).getImageUrl();
        ret.likeCount = likeCount;
        ret.chatCount = chatCount;

        Region region = post.getRegion();
        ret.region = region.getCounty() + " " + region.getTown() + " " + region.getDistrict();
        return ret;
    }
}
