package app.melon.web.sock;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class ChatSession {

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
}
