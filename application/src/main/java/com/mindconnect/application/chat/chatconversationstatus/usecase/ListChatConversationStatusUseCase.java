package com.mindconnect.application.chat.chatconversationstatus.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatconversationstatus.dto.ChatConversationStatusResponse;
import com.mindconnect.domain.chat.chatconversationstatus.port.repository.ChatConversationStatusRepository;

public class ListChatConversationStatusUseCase {

    private final ChatConversationStatusRepository statusRepository;

    public ListChatConversationStatusUseCase(ChatConversationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<ChatConversationStatusResponse> execute() {
        return statusRepository.findAll()
                .stream()
                .map(ChatConversationStatusResponse::from)
                .toList();
    }
}