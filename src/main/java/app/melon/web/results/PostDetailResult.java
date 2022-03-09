package app.melon.web.results;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.user.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResult {

    private UserData user;
    private PostData post;

    public static ResponseEntity<PostDetailResult> from(User user, Post post, List<PostImage> images, int likeCount, boolean likedByMe) {
        PostDetailResult ret = new PostDetailResult();
        ret.user = UserData.from(user);
        ret.post = PostData.from(post, images, likeCount, likedByMe);
        return ApiResult.ok(ret);
    }

    @Getter
    private static class UserData {
        private long id;
        private String imageUrl;
        private String username;

        private static UserData from(User user) {
            UserData data = new UserData();
            data.id = user.getId();
            data.imageUrl = user.getImageUrl();
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
        private boolean likedByMe;
        private int likeCount;
        private int chatCount;
        private int viewCount;
        private List<String> imageUrls;
        private LocalDateTime createdTime;

        public static PostData from(Post post, List<PostImage> images, int likeCount, boolean likedByMe) {
            PostData data = new PostData();
            data.id = post.getId();
            data.title = post.getTitle();
            data.likedByMe = likedByMe;
            data.likeCount = likeCount;
            data.viewCount = post.getViewCount();
            data.body = post.getBody();
            data.price = post.getPrice();
            data.createdTime = post.getCreatedTime();
            data.imageUrls = images.stream().map(PostImage::getImageUrl).collect(Collectors.toList());
            return data;
        }
    }
}
