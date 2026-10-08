package com.mindconnect.application.chat.chatescalationstatushistory.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.mindconnect.domain.chat.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;

public class ListChatEscalationStatusHistoryUseCase {

    private final ChatEscalationStatusHistoryRepository historyRepository;

    public ListChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<ChatEscalationStatusHistoryResponse> execute() {
        return historyRepository.findAll()
                .stream()
                .map(ChatEscalationStatusHistoryResponse::from)
                .toList();
    }
}