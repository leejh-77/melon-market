package app.melon.domain.services;

import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.models.chat.Chat;
import app.melon.domain.models.post.Post;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.chat.ChatRepository;
import app.melon.infrastructure.repositories.post.PostRepository;
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
    private final PostRepository postRepository;

    public ChatService(ChatRepository chatRepository, PostRepository postRepository) {
        this.chatRepository = chatRepository;
        this.postRepository = postRepository;
    }

    public List<Chat> getChatListWithMeAndUserId(User user, long postId) throws ApiException {
        Optional<Post> opPost = this.postRepository.findById(postId);
        if (opPost.isEmpty()) {
            throw ApiException.of(Errors.ItemNotFound);
        }
        return this.chatRepository.findByMeAndTargetUser(user.getId(), opPost.get().getUser().getId(), postId);
    }
}
