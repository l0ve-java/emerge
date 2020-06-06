package org.departure.emerge.app.service;

import lombok.RequiredArgsConstructor;
import org.departure.emerge.app.dto.Chat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private List<Chat> allChats = new ArrayList<>();

    public Chat getTopicChat(String topicId) {
        return allChats.stream().filter(c -> c.getTopicId().equals(topicId))
                .findFirst()
                .orElseGet(() -> {
                    synchronized (ChatService.class) {
                        final Chat chat = Chat.builder()
                                .answers(2L)
                                .topicId(topicId)
                                .chatId(UUID.randomUUID().toString())
                                .build();
                        allChats.add(chat);
                        return chat;
                    }
                });
    }

}
