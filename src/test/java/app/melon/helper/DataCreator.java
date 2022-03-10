package app.melon.helper;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.post.PostLike;
import app.melon.domain.models.user.User;

import java.time.LocalDateTime;

public class DataCreator {

    public static User newUser() {
        String emailAddress = genRandomString("test") + "@email.com";
        String username = "Name!@";
        String password = "password@#$";
        return User.create(emailAddress, username, password);
    }

    public static Post newPost() {
        return newPost(null);
    }

    public static Post newPost(User user) {
        Post post = new Post();
        post.setTitle(genRandomString("title"));
        post.setBody(genRandomString("body"));
        post.setViewCount(0);
        post.setCreatedTime(LocalDateTime.now());
        post.setPrice(12000);
        post.setUser(user);
        return post;
    }

    public static PostImage newPostImage(Post post) {
        return PostImage.create(post, genRandomString("1"), LocalDateTime.now());
    }

    public static PostLike newLike(User user, Post post) {
        return PostLike.create(post, user);
    }

    private static String genRandomString(String v) {
        return v + "." + System.currentTimeMillis();
    }
}
