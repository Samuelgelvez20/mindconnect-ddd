package com.mindconnect.application.chat.chatescalation.usecase;

import com.mindconnect.application.chat.chatescalation.dto.ChatEscalationResponse;
import com.mindconnect.application.chat.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalation.port.repository.ChatEscalationRepository;

public class GetChatEscalationByIdUseCase {

    private final ChatEscalationRepository escalationRepository;

    public GetChatEscalationByIdUseCase(ChatEscalationRepository escalationRepository) {
        this.escalationRepository = escalationRepository;
    }

    public ChatEscalationResponse execute(ChatEscalationId id) {
        return escalationRepository.findById(id)
                .map(ChatEscalationResponse::from)
                .orElseThrow(() -> new ChatEscalationNotFoundApplicationException(id));
    }
}