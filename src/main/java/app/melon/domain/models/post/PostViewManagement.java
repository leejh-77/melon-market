package app.melon.domain.models.post;

import app.melon.infrastructure.repositories.post.PostRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PostViewManagement {

    private static final String VIEW_RANK_KEY = "viewRank";

    private final Set<Long> viewedPostIds = new HashSet<>();

    private final RedisTemplate<String, Long> redisTemplate;

    public PostViewManagement(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void handle(Post post) {
        if (this.viewedPostIds.contains(post.getId())) {
            return;
        }

        post.incrementViewCount();
        this.redisTemplate.opsForZSet().incrementScore(VIEW_RANK_KEY, post.getId(), 1);
        this.viewedPostIds.add(post.getId());
    }

    public List<Long> getViewRankList(int range) {
        ZSetOperations<String, Long> ops = this.redisTemplate.opsForZSet();
        Set<Long> set = ops.reverseRange(VIEW_RANK_KEY, 0, range);
        if (set == null) {
            return List.of();
        }
        return new ArrayList<>(set);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void clearCache() {
        this.redisTemplate.delete(VIEW_RANK_KEY);
    }
}
