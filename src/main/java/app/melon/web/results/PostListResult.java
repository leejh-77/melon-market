package app.melon.web.results;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import lombok.Getter;

@Getter
public class PostListResult {

    private long id;
    private String title;
    private int price;
    private int likeCount;
    private int chatCount;
    private String imageUrl;

    public static PostListResult from(Post post, PostImage image, int likeCount) {
        PostListResult ret = new PostListResult();
        ret.id = post.getId();
        ret.price = post.getPrice();
        ret.title = post.getTitle();
        ret.imageUrl = image.getImageUrl();
        ret.likeCount = likeCount;
        return ret;
    }
}
