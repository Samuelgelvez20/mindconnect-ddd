package com.mindconnect.application.chat.chatescalationstatushistory.usecase;

import com.mindconnect.application.chat.chatescalationstatushistory.command.UpdateChatEscalationStatusHistoryCommand;
import com.mindconnect.application.chat.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.mindconnect.application.chat.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class UpdateChatEscalationStatusHistoryUseCase {

    private final ChatEscalationStatusHistoryRepository historyRepository;

    public UpdateChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public ChatEscalationStatusHistoryResponse execute(UpdateChatEscalationStatusHistoryCommand command) {

        var history = historyRepository.findById(command.id())
                .orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(command.id()));

        history.update(
                command.escalationId(),
                command.escalationStatusId(),
                command.changedAt());

        return ChatEscalationStatusHistoryResponse.from(historyRepository.save(history));
    }
}