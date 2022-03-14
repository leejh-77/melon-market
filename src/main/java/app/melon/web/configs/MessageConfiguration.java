package app.melon.web.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

@Configuration
public class MessageConfiguration {

    @Bean
    public FanoutExchange chatEventExchange() {
        return new FanoutExchange("melon.chat.events", true, false);
    }

    @Bean
    public Queue chatTrackingQueue() {
        return new Queue("melon.chat.tracking", true);
    }

    @Bean
    public Binding bindingChatTracking(FanoutExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
