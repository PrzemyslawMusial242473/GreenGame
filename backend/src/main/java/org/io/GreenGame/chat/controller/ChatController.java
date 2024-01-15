package org.io.GreenGame.chat.controller;

import org.io.GreenGame.chat.model.ChatMessage;
import org.io.GreenGame.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("secured/api/chat")
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
            @RequestParam Long sender,
            @RequestParam Long receiver) {
        List<ChatMessage> messages = chatService.getChatMessages(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/block")
    public ResponseEntity<String> blockUser(
            @RequestParam Long user,
            @RequestParam Long contact) {
        chatService.blockUser(user, contact);
        return ResponseEntity.ok("User blocked successfully");
    }

    @PostMapping("/unblock")
    public ResponseEntity<String> unblockUser(
            @RequestParam Long user,
            @RequestParam Long contact) {
        chatService.unblockUser(user, contact);
        return ResponseEntity.ok("User unblocked successfully");
    }

    @GetMapping("/isBlocked")
    public ResponseEntity<Boolean> isUserBlocked(
            @RequestParam Long user,
            @RequestParam Long contact) {
        boolean isBlocked = chatService.isUserBlocked(user, contact);
        return ResponseEntity.ok(isBlocked);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ChatMessage>> getUnreadMessages(@RequestParam Long user) {
        List<ChatMessage> unreadMessages = chatService.getUnreadMessages(user);
        return ResponseEntity.ok(unreadMessages);
    }

    @PostMapping("/markAsRead")
    public ResponseEntity<String> markMessagesAsRead(
            @RequestParam Long user,
            @RequestParam Long sender) {
        chatService.markMessagesAsRead(user, sender);
        return ResponseEntity.ok("Messages marked as read successfully");
    }
}
