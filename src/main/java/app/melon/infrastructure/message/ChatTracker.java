package app.melon.infrastructure.message;

import app.melon.domain.models.chat.Chat;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.chat.ChatRepository;
import app.melon.infrastructure.repositories.post.PostRepository;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChatTracker {

    private final ChatRepository chatRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ChatTracker(ChatRepository chatRepository,
                       UserRepository userRepository,
                       PostRepository postRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @RabbitListener(queues = "#{chatTrackingQueue}")
    public void receive(ChatEvent event) {
        Optional<User> opFrom = this.userRepository.findById(event.getFrom());
        Optional<User> opTo = this.userRepository.findById(event.getTo());
        Optional<Post> opPost = this.postRepository.findById(event.getPost());
        if (opFrom.isEmpty() || opTo.isEmpty() || opPost.isEmpty()) {
            return;
        }

        Chat chat = Chat.create(opFrom.get(), opTo.get(), opPost.get(), event.getMessage(), event.getCreatedAt());
        this.chatRepository.save(chat);
    }
}
