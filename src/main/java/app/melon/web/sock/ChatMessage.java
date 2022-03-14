package app.melon.web.sock;

import app.melon.infrastructure.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;

import java.util.Arrays;
import java.util.HashMap;

public class ChatMessage {

    enum Type {
        Authenticated("authenticated"),
        Message("normal"),
        Subscribe("subscribe"),
        Unsubscribe("unsubscribe"),
        Error("error");

        private final String type;

        Type(String type) {
            this.type = type;
        }

        private static Type from(String type) {
            for (Type t : Type.values()) {
                if (t.type.equals(type)) {
                    return t;
                }
            }
            return Message;
        }
    }

    public static TextMessage message(Long from, String message) {
        return create(Type.Message, from, message);
    }

    public static TextMessage authenticated() {
        return create(Type.Authenticated);
    }

    public static TextMessage error(String message) {
        return create(Type.Error, null, message);
    }

    private static TextMessage create(Type type) {
        return create(type, null, null);
    }

    private static TextMessage create(Type type, Long from, String message) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type.type);
        if (from != null) {
            map.put("from", from);
        }
        if (message != null) {
            map.put("message", message);
        }
        return new TextMessage(JsonUtils.toJson(map));
    }

    public static ChatMessage.Incoming parse(TextMessage message) {
        return JsonUtils.toObject(message.getPayload(), Incoming.class);
    }

    public static class Incoming {
        @JsonProperty
        private String type;
        private String content;
        private long to;

        public Type getType() {
            return Type.from(this.type);
        }

        public String getContent() {
            return content;
        }

        public long getTo() {
            return to;
        }
    }
}
