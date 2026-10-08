package com.mindconnect.application.chat.chatescalationstatushistory.usecase;

import com.mindconnect.application.chat.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.mindconnect.application.chat.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;

public class GetChatEscalationStatusHistoryByIdUseCase {

    private final ChatEscalationStatusHistoryRepository historyRepository;

    public GetChatEscalationStatusHistoryByIdUseCase(ChatEscalationStatusHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public ChatEscalationStatusHistoryResponse execute(ChatEscalationStatusHistoryId id) {
        return historyRepository.findById(id)
                .map(ChatEscalationStatusHistoryResponse::from)
                .orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(id));
    }
}