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

    @Query("SELECT c FROM Chat c WHERE (c.to = :me AND c.from = :target) " +
            "OR (c.from = :me AND c.to = :target) ORDER BY c.createdAt DESC")
    List<Chat> findByMeAndTargetUser(@Param("me") User me,
                                     @Param("target") User target);
}
