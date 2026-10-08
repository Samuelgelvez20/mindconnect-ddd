package com.mindconnect.application.chat.chatescalationstatushistory.usecase;

import com.mindconnect.application.chat.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;

public class DeleteChatEscalationStatusHistoryUseCase {

    private final ChatEscalationStatusHistoryRepository historyRepository;

    public DeleteChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void execute(ChatEscalationStatusHistoryId id) {

        var history = historyRepository.findById(id)
                .orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(id));

        history.delete();
        historyRepository.delete(history);
    }
}