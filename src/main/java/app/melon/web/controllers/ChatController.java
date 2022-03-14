package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.domain.models.chat.Chat;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.services.ChatService;
import app.melon.web.results.ApiResult;
import app.melon.web.results.ChatResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    public ResponseEntity<List<ChatResult>> getChatList(@RequestParam("userId") long userId,
                                                        @AuthenticationPrincipal SimpleUser user) throws ApiException {
        List<Chat> chatList = this.chatService.getChatListWithMeAndUserId(user.getUser(), userId);
        List<ChatResult> results = chatList.stream().map(ChatResult::from).collect(Collectors.toList());
        return ApiResult.ok(results);
    }
}
