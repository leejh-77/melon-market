package app.melon.web.results;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResult {

    private long id;
    private String title;
    private int price;
    private int likeCount;
    private int chatCount;
    private List<String> imageUrls;

    public static PostDetailResult from(Post post, List<PostImage> images) {
        PostDetailResult ret = new PostDetailResult();
        ret.id = post.getId();
        ret.title = post.getTitle();
        ret.price = post.getPrice();
        ret.imageUrls = images.stream().map(PostImage::getImageName).collect(Collectors.toList());
        return ret;
    }

}
