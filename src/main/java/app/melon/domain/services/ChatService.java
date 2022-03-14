package app.melon.domain.services;

import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.models.chat.Chat;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.chat.ChatRepository;
import app.melon.infrastructure.repositories.user.UserRepository;
import app.melon.web.results.ChatResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public List<Chat> getChatListWithMeAndUserId(User user, long targetUserId) throws ApiException {
        Optional<User> targetUser = this.userRepository.findById(targetUserId);
        if (targetUser.isEmpty()) {
            throw ApiException.of(Errors.UserNotFound);
        }
        return this.chatRepository.findByMeAndTargetUser(user, targetUser.get());
    }
}
