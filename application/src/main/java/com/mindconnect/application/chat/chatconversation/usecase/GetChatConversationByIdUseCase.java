package com.mindconnect.application.chat.chatconversation.usecase;

import com.mindconnect.application.chat.chatconversation.dto.ChatConversationResponse;
import com.mindconnect.application.chat.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversation.port.repository.ChatConversationRepository;

public class GetChatConversationByIdUseCase {

    private final ChatConversationRepository conversationRepository;

    public GetChatConversationByIdUseCase(ChatConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public ChatConversationResponse execute(ChatConversationId id) {
        return conversationRepository.findById(id)
                .map(ChatConversationResponse::from)
                .orElseThrow(() -> new ChatConversationNotFoundApplicationException(id));
    }
}