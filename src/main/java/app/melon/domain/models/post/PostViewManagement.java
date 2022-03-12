package app.melon.domain.models.post;

import app.melon.infrastructure.repositories.post.PostRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PostViewManagement {

    private final Set<Long> viewedPostIds = new HashSet<>();

    public void handle(Post post) {
        if (this.viewedPostIds.contains(post.getId())) {
            return;
        }
        post.incrementViewCount();
        this.viewedPostIds.add(post.getId());
    }
}
