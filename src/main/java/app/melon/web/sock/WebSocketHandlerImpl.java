package app.melon.web.sock;

import app.melon.infrastructure.message.ChatEvent;
import app.melon.infrastructure.message.ChatEventPublisher;
import app.melon.infrastructure.utils.JwtManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

@Component
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandlerImpl.class);

    private final JwtManager jwtManager;
    private final ChatEventPublisher chatEventPublisher;

    public WebSocketHandlerImpl(JwtManager jwtManager, ChatEventPublisher chatEventPublisher) {
        this.jwtManager = jwtManager;
        this.chatEventPublisher = chatEventPublisher;
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
                ChatHub.send(chatSession, chat.getTo(), chat.getPostId(), chat.getContent());
                break;
        }
        this.chatEventPublisher.publish(ChatEvent.create(
                chatSession.getUserId(), chat.getTo(), chat.getPostId(),
                chat.getContent(), LocalDateTime.now()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ChatSession chatSession = ChatSession.from(session);
        ChatHub.unsubscribe(chatSession);
    }
}
