package app.melon.infrastructure.message;

import app.melon.domain.models.user.User;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ChatEvent implements Serializable {

    private long from;
    private long to;
    private long post;
    private String message;
    private LocalDateTime createdAt;

    public static ChatEvent create(long from, long to, long post, String message, LocalDateTime createdAt) {
        ChatEvent e = new ChatEvent();
        e.from = from;
        e.to = to;
        e.post = post;
        e.message = message;
        e.createdAt = createdAt;
        return e;
    }
}
