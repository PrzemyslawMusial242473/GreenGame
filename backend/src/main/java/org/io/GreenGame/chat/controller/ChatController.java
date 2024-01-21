package org.io.GreenGame.chat.controller;

import org.io.GreenGame.chat.model.ChatMessage;
import org.io.GreenGame.chat.service.ChatService;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("secured/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private AuthServiceImplementation authServiceImplementation;

    @GetMapping("/")
    public RedirectView entryPoint() {
        return new RedirectView("http://localhost:8101/");
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam Long receiverId,
            @RequestParam String content
    ) {

        Long senderId = getIdOfLoggedUser();

        ChatMessage message = new ChatMessage(null, senderId, receiverId, content, LocalDateTime.now(), false);
        chatService.sendChatMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @RequestParam Long receiver) {
        List<ChatMessage> messages = chatService.getChatMessages(getIdOfLoggedUser(), receiver);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ChatMessage>> getUnreadMessages(@RequestParam Long user) {
        List<ChatMessage> unreadMessages = chatService.getUnreadMessages(user);
        return ResponseEntity.ok(unreadMessages);
    }

    @PostMapping("/markAsRead")
    public ResponseEntity<String> markMessageAsRead(
            @RequestParam Long sender,
            @RequestParam Long messageParam) {
        chatService.markMessageAsRead(getIdOfLoggedUser(), sender, messageParam);
        return ResponseEntity.ok("Message marked as read successfully");
    }

    private Long getIdOfLoggedUser(){
        return authServiceImplementation.getUserFromSession().getId();
    }

}
