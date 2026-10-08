package com.mindconnect.application.chat.chatescalationstatushistory.usecase;

import com.mindconnect.application.chat.chatescalationstatushistory.command.RegisterChatEscalationStatusHistoryCommand;
import com.mindconnect.application.chat.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.mindconnect.domain.chat.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class RegisterChatEscalationStatusHistoryUseCase {

    private final ChatEscalationStatusHistoryRepository historyRepository;

    public RegisterChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public ChatEscalationStatusHistoryResponse execute(RegisterChatEscalationStatusHistoryCommand command) {

        ChatEscalationStatusHistory history = ChatEscalationStatusHistory.register(
                command.escalationId(),
                command.escalationStatusId(),
                command.changedAt());

        return ChatEscalationStatusHistoryResponse.from(historyRepository.save(history));
    }
}