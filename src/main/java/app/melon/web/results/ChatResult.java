package app.melon.web.results;

import app.melon.domain.models.chat.Chat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatResult {

    private long id;
    private long from;
    private long to;
    private String message;
    private LocalDateTime createdAt;

    public static ChatResult from(Chat chat) {
        ChatResult ret = new ChatResult();
        ret.id = chat.getId();
        ret.from = chat.getFrom().getId();
        ret.to = chat.getTo().getId();
        ret.message = chat.getMessage();
        ret.createdAt = chat.getCreatedAt();
        return ret;
    }
}
