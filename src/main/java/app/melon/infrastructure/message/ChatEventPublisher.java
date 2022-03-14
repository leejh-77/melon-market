package app.melon.infrastructure.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ChatEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange exchange;

    public ChatEventPublisher(RabbitTemplate rabbitTemplate, FanoutExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void publish(ChatEvent event) {
        try {
            this.rabbitTemplate.convertAndSend(exchange.getName(), "", event);
        } catch (AmqpException e) {
            logger.error("Failed to publish event");
        }
    }
}
