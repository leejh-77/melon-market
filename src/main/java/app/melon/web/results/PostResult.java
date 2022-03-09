package app.melon.web.results;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import lombok.Getter;

@Getter
public class PostResult {

    private long id;
    private String title;
    private int price;
    private int likeCount;
    private int chatCount;
    private String imageUrl;

    public static PostResult from(Post post, PostImage image, int likeCount) {
        PostResult ret = new PostResult();
        ret.id = post.getId();
        ret.price = post.getPrice();
        ret.title = post.getTitle();
        ret.imageUrl = image.getImageUrl();
        ret.likeCount = likeCount;
        return ret;
    }
}
