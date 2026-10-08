package com.mindconnect.application.chat.chatescalationstatus.usecase;

import com.mindconnect.application.chat.chatescalationstatus.dto.ChatEscalationStatusResponse;
import com.mindconnect.application.chat.chatescalationstatus.exception.ChatEscalationStatusNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.domain.chat.chatescalationstatus.port.repository.ChatEscalationStatusRepository;

public class GetChatEscalationStatusByIdUseCase {

    private final ChatEscalationStatusRepository statusRepository;

    public GetChatEscalationStatusByIdUseCase(ChatEscalationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ChatEscalationStatusResponse execute(ChatEscalationStatusId id) {
        return statusRepository.findById(id)
                .map(ChatEscalationStatusResponse::from)
                .orElseThrow(() -> new ChatEscalationStatusNotFoundApplicationException(id));
    }
}