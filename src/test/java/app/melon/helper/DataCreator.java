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
        return newPost(null);
    }

    public static Post newPost(User user) {
        Post post = new Post();
        post.setTitle(genRandomString("title"));
        post.setBody(genRandomString("body"));
        post.setViewCount(0);
        post.setCreatedTime(LocalDateTime.now().withNano(0));
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

    public static void insertRegions(RegionRepository repository) throws Exception {
        DefaultLineMapper<Region> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("code", "county", "town", "district");
        tokenizer.setDelimiter("\t");
        tokenizer.setStrict(false);
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<Region> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Region.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        String path = ResourceUtils.getURL("src/test/resources/regions.tsv").toString();
        DefaultResourceLoader loader = new DefaultResourceLoader();
        FlatFileItemReader<Region> reader = new FlatFileItemReaderBuilder<Region>()
                .name("TSV Reader")
                .resource(loader.getResource(path))
                .lineMapper(lineMapper)
                .linesToSkip(1).build();

        reader.open(new ExecutionContext());
        Region region;
        while ((region = reader.read()) != null) {
            repository.save(region);
        }
        reader.close();
    }

    private static String genRandomString(String v) {
        return v + "." + System.currentTimeMillis();
    }

    public static Region newRegion() {
        return Region.create("1111111111", "서울특별시", "강남구", null);
    }
}
