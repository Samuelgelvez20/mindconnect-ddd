package com.mindconnect.application.chat.chatmessage.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatmessage.dto.ChatMessageResponse;
import com.mindconnect.domain.chat.chatmessage.port.repository.ChatMessageRepository;

public class ListChatMessageUseCase {

    private final ChatMessageRepository messageRepository;

    public ListChatMessageUseCase(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<ChatMessageResponse> execute() {
        return messageRepository.findAll()
                .stream()
                .map(ChatMessageResponse::from)
                .toList();
    }
}