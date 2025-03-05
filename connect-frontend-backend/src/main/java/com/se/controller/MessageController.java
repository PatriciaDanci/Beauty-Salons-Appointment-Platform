package com.se.controller;

import com.se.model.Message;
import com.se.model.User;
import com.se.service.MessageService;
import com.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    // Dummy: Send a message
    @PostMapping
    public String sendMessage(@RequestBody SendMessageRequest request) {
        // Placeholder for real implementation
        return "Message sending functionality not implemented yet.";
    }

    // Dummy: Get chat history between two users
    @GetMapping("/{senderId}/{receiverId}")
    public List<Message> getChatHistory(@PathVariable Long senderId, @PathVariable Long receiverId) {
        // Returning an empty list for now
        return new ArrayList<>();
    }

    // Dummy: Get unread messages for a user
    @GetMapping("/unread/{receiverId}")
    public List<Message> getUnreadMessages(@PathVariable Long receiverId) {
        // Returning an empty list for now
        return new ArrayList<>();
    }

    // Dummy: Mark a message as read
    @PostMapping("/mark-read/{messageId}")
    public String markMessageAsRead(@PathVariable Long messageId) {
        // Placeholder for real implementation
        return "Mark message as read functionality not implemented yet.";
    }

    // DTO for sending a message
    public static class SendMessageRequest {
        private Long senderId;
        private Long receiverId;
        private String content;

        // Getters and Setters
        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public Long getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Long receiverId) {
            this.receiverId = receiverId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
