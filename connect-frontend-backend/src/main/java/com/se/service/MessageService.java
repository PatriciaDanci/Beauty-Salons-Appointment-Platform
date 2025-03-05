package com.se.service;

import com.se.model.Message;
import com.se.model.User;
import com.se.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    // Send a message
    public Message sendMessage(User sender, User receiver, String content) {
        Message message = new Message(sender, receiver, content, LocalDateTime.now(), false);
        return messageRepository.save(message);
    }

    // Get chat history between two users
    public List<Message> getChatHistory(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    // Get unread messages for a user
    public List<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiverIdAndIsReadFalse(receiverId);
    }

    // Mark a message as read
    public void markMessageAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new RuntimeException("Message not found"));
        message.setRead(true);
        messageRepository.save(message);
    }
}
