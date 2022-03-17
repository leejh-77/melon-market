package app.melon.infrastructure.repositories.post;

import app.melon.domain.models.post.Post;
import app.melon.domain.models.post.QPost;
import app.melon.domain.models.post.QPostLike;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryExtensionImpl implements PostRepositoryExtension {

    private final EntityManager em;

    public PostRepositoryExtensionImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Post> findLikedPosts(int count, long userId, String region) {
        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;
        JPAQuery<Post> query = new JPAQueryFactory(this.em)
                .selectFrom(post);

        BooleanExpression where = post.id.in(
                JPAExpressions.select(postLike.post.id)
                        .from(postLike)
                        .where(postLike.user.id.eq(userId))
        );
        if (region != null) {
            where.and(post.region.code.like(region + '%'));
        }
        query.where(where);
        query.orderBy(post.createdTime.desc());
        query.limit(count);
        return query.fetch();
    }

    @Override
    public List<Post> findRecentPosts(int count, String queryText, String region) {
        QPost post = QPost.post;
        JPAQuery<Post> query = new JPAQueryFactory(this.em)
                .selectFrom(post);

        BooleanExpression where = null;
        if (queryText != null) {
            where = post.title.like(queryText);
        }
        if (region != null) {
            BooleanExpression ex = post.region.code.like(region + '%');
            where = where == null ? ex : where.and(ex);
        }
        query.where(where);
        query.orderBy(post.createdTime.desc());
        query.limit(count);
        return query.fetch();
    }

    @Override
    public List<Post> findMyPosts(int listQueryCount, long userId) {
        JPAQueryFactory query = new JPAQueryFactory(this.em);
        QPost post = QPost.post;
        return query.selectFrom(post)
                .where(post.user.id.eq(userId))
                .orderBy(post.createdTime.desc())
                .fetch();
    }
}
