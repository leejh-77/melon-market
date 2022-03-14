package app.melon.web.sock;

import app.melon.infrastructure.utils.JwtManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandlerImpl.class);

    private final JwtManager jwtManager;

    public WebSocketHandlerImpl(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ChatSession chatSession = ChatSession.from(session);
        String token = chatSession.getToken();
        long userId = this.jwtManager.extractId(token);
        chatSession.setUserId(userId);
        chatSession.send(ChatMessage.authenticated());

        logger.info("Received connection : userId - " + chatSession.getUserId());

        ChatHub.subscribe(chatSession);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatSession chatSession = ChatSession.from(session);
        ChatMessage.Incoming chat = ChatMessage.parse(message);

        switch (chat.getType()) {
            case Message:
                ChatHub.send(chatSession, chat.getTo(), chat.getContent());
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ChatSession chatSession = ChatSession.from(session);
        ChatHub.unsubscribe(chatSession);
    }
}
