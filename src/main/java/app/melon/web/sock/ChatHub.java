package app.melon.web.sock;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatHub {

    private static final Map<Long, ChatSession> sessions = new ConcurrentHashMap<>();

    public static void subscribe(ChatSession session) {
        sessions.put(session.getUserId(), session);
    }

    public static void unsubscribe(ChatSession session) {
        sessions.remove(session.getUserId());
    }

    public static void send(ChatSession chatSession, long to, long postId, String content) throws IOException {
        ChatSession session = sessions.get(to);
        if (session != null) {
            session.send(ChatMessage.message(chatSession.getUserId(), postId, content));
        }
    }
}
