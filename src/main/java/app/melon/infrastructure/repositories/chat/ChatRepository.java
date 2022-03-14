package app.melon.infrastructure.repositories.chat;

import app.melon.domain.models.chat.Chat;
import app.melon.domain.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE (c.to.id = :me AND c.from.id = :target) " +
            "OR (c.from.id = :me AND c.to.id = :target) AND c.post.id = :post ORDER BY c.createdAt DESC")
    List<Chat> findByMeAndTargetUser(@Param("me") long me,
                                     @Param("target") long target,
                                     @Param("post") long post);

    @Query("SELECT COUNT(DISTINCT c.from.id) FROM Chat c " +
            "WHERE c.post.id = :postId AND c.from.id NOT IN (SELECT p.user.id FROM Post p WHERE p.id = :postId)")
    int findChatCount(long postId);
}
