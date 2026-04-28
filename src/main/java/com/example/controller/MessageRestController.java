package com.example.controller;

import com.example.model.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam String user1, @RequestParam String user2) {
        return messageRepository.findConversation(user1, user2);
    }

    @GetMapping("/contacts")
    public List<String> getContacts(@RequestParam String user) {
        return messageRepository.findUniqueContacts(user);
    }

    @GetMapping("/unread-count")
    public int getUnreadCount(@RequestParam String receiver) {
        return messageRepository.findByReceiverAndIsReadFalse(receiver).size();
    }

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
        message.setTimestamp(java.time.LocalDateTime.now());
        return messageRepository.save(message);
    }
}
