package com.mindconnect.application.chat.chatconversationstatus.usecase;

import com.mindconnect.application.chat.chatconversationstatus.dto.ChatConversationStatusResponse;
import com.mindconnect.application.chat.chatconversationstatus.exception.ChatConversationStatusNotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.chatconversationstatus.port.repository.ChatConversationStatusRepository;

public class GetChatConversationStatusByIdUseCase {

    private final ChatConversationStatusRepository statusRepository;

    public GetChatConversationStatusByIdUseCase(ChatConversationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ChatConversationStatusResponse execute(ChatConversationStatusId id) {
        return statusRepository.findById(id)
                .map(ChatConversationStatusResponse::from)
                .orElseThrow(() -> new ChatConversationStatusNotFoundApplicationException(id));
    }
}