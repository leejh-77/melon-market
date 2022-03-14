package app.melon.web.sock;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class ChatSession {

    private static final String ATTR_USER_ID = "userId";

    private WebSocketSession session;

    public static ChatSession from(WebSocketSession session) {
        ChatSession s = new ChatSession();
        s.session = session;
        return s;
    }

    public String getToken() {
        URI uri = this.session.getUri();
        UriComponents comps = UriComponentsBuilder.fromUri(uri).build();
        return comps.getQueryParams().getFirst("token");
    }

    public void setUserId(long userId) {
        this.setAttribute(ATTR_USER_ID, userId);
    }

    public long getUserId() {
        return (long) this.getAttribute(ATTR_USER_ID);
    }

    public void setAttribute(String key, Object value) {
        this.session.getAttributes().put(key, value);
    }

    public Object getAttribute(String key) {
        return this.session.getAttributes().get(key);

    }

    public void send(TextMessage message) throws IOException {
        this.session.sendMessage(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatSession that = (ChatSession) o;
        return this.session.getId().equals(that.session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(session);
    }
}
