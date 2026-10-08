package com.mindconnect.application.chat.chatconversation.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatconversation.dto.ChatConversationResponse;
import com.mindconnect.domain.chat.chatconversation.port.repository.ChatConversationRepository;

public class ListChatConversationUseCase {

    private final ChatConversationRepository conversationRepository;

    public ListChatConversationUseCase(ChatConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<ChatConversationResponse> execute() {
        return conversationRepository.findAll()
                .stream()
                .map(ChatConversationResponse::from)
                .toList();
    }
}