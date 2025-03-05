package com.se.repository;

import com.se.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Get messages between two users
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // Get all messages for a user (sent or received)
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    // Get unread messages for a user
    List<Message> findByReceiverIdAndIsReadFalse(Long receiverId);
}
