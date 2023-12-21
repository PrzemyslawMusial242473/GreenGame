package org.io.GreenGame.user.controller;

import org.io.GreenGame.user.model.ChatMessage;
import org.io.GreenGame.user.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessage message) {

        chatService.sendChatMessage(message);

        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @RequestParam String sender,
            @RequestParam String receiver) {
        List<ChatMessage> messages = chatService.getChatMessages(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/block")
    public ResponseEntity<String> blockUser(
            @RequestParam String user,
            @RequestParam String contact) {
        chatService.blockUser(user, contact);
        return ResponseEntity.ok("User blocked successfully");
    }

    @PostMapping("/unblock")
    public ResponseEntity<String> unblockUser(
            @RequestParam String user,
            @RequestParam String contact) {
        chatService.unblockUser(user, contact);
        return ResponseEntity.ok("User unblocked successfully");
    }

    @GetMapping("/isBlocked")
    public ResponseEntity<Boolean> isUserBlocked(
            @RequestParam String user,
            @RequestParam String contact) {
        boolean isBlocked = chatService.isUserBlocked(user, contact);
        return ResponseEntity.ok(isBlocked);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ChatMessage>> getUnreadMessages(@RequestParam String user) {
        List<ChatMessage> unreadMessages = chatService.getUnreadMessages(user);
        return ResponseEntity.ok(unreadMessages);
    }

    @PostMapping("/markAsRead")
    public ResponseEntity<String> markMessagesAsRead(
            @RequestParam String user,
            @RequestParam String sender) {
        chatService.markMessagesAsRead(user, sender);
        return ResponseEntity.ok("Messages marked as read successfully");
    }
}
