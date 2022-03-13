package app.melon.helper;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.PostImage;
import app.melon.domain.models.post.PostLike;
import app.melon.domain.models.region.Region;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.region.RegionRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class DataCreator {

    public static User newUser() {
        String emailAddress = genRandomString("test") + "@email.com";
        String username = "Name!@";
        String password = "password@#$";
        return User.create(emailAddress, username, password);
    }

    public static Post newPost() {
        return newPost(null, null);
    }

    public static Post newPost(User user, Region region) {
        Post post = new Post();
        post.setTitle(genRandomString("title"));
        post.setBody(genRandomString("body"));
        post.setCreatedTime(LocalDateTime.now().withNano(0));
        post.setPrice(12000);
        post.setUser(user);
        post.setRegion(region);
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

    public static Region newRegion() {
        return Region.create("1111111111", "서울특별시", "강남구", "동그리동동");
    }
}
