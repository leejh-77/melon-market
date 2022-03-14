package app.melon.web.sock;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatHub {

    private static final Map<Long, Set<ChatSession>> subscriptions = new ConcurrentHashMap<>();
    private static final Map<Long, Set<Long>> subscriptionMap = new ConcurrentHashMap<>();

    public static void subscribe(long to, ChatSession session) {
        Set<ChatSession> subs = subscriptions.computeIfAbsent(to, k -> new HashSet<>());
        synchronized (subs) {
            subs.add(session);
        }
        Set<Long> subSet = subscriptionMap.computeIfAbsent(session.getUserId(), k -> new HashSet<>());
        subSet.add(to);
    }

    public static void unsubscribe(long to, ChatSession session) {
        Set<Long> set = subscriptionMap.get(session.getUserId());
        set.remove(to);

        Set<ChatSession> sub = subscriptions.get(to);
        synchronized (sub) {
            sub.remove(session);
        }
    }

    public static void unsubscribeAll(ChatSession session) {
        Set<Long> set = subscriptionMap.get(session.getUserId());

        for (long id : set) {
            Set<ChatSession> sessions = subscriptions.get(id);
            synchronized (sessions) {
                sessions.remove(session);
            }
        }
    }

    public static void send(ChatSession chatSession, String content) throws IOException {
        Set<ChatSession> subs = subscriptions.get(chatSession.getUserId());
        synchronized (subs) {
            for (ChatSession sub : subs) {
                sub.send(ChatMessage.normal(content));
            }
        }
    }
}
