package app.melon.web.results;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResult {

    private UserData user;
    private PostData post;

    public static PostDetailResult from(User user, Post post, List<PostImage> images) {
        PostDetailResult ret = new PostDetailResult();
        ret.user = UserData.from(user);
        ret.post = PostData.from(post, images);
        return ret;
    }

    @Getter
    private static class UserData {
        private long id;
        private String imageUrl;
        private String username;

        private static UserData from(User user) {
            UserData data = new UserData();
            data.id = user.getId();
            data.imageUrl = user.getImagePath();
            data.username = user.getUsername();
            return data;
        }
    }

    @Getter
    private static class PostData {
        private long id;
        private String title;
        private String body;
        private int price;
        private int likeCount;
        private int chatCount;
        private int viewCount;
        private List<String> imageUrls;
        private LocalDateTime createdTime;

        public static PostData from(Post post, List<PostImage> images) {
            PostData data = new PostData();
            data.id = post.getId();
            data.title = post.getTitle();
            data.body = post.getBody();
            data.price = post.getPrice();
            data.createdTime = post.getCreatedTime();
            data.imageUrls = images.stream().map(PostImage::getImageName).collect(Collectors.toList());
            return data;
        }
    }
}
