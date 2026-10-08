package com.mindconnect.application.chat.chatescalationstatus.usecase;

import com.mindconnect.application.chat.chatescalationstatus.command.RegisterChatEscalationStatusCommand;
import com.mindconnect.application.chat.chatescalationstatus.dto.ChatEscalationStatusResponse;
import com.mindconnect.application.chat.chatescalationstatus.exception.ChatEscalationStatusAlreadyExistsApplicationException;
import com.mindconnect.domain.chat.chatescalationstatus.model.aggregate.ChatEscalationStatus;
import com.mindconnect.domain.chat.chatescalationstatus.port.repository.ChatEscalationStatusRepository;

public class RegisterChatEscalationStatusUseCase {

    private final ChatEscalationStatusRepository statusRepository;

    public RegisterChatEscalationStatusUseCase(ChatEscalationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ChatEscalationStatusResponse execute(RegisterChatEscalationStatusCommand command) {

        ChatEscalationStatus status = ChatEscalationStatus.register(command.name());

        if (statusRepository.existsByName(status.name())) {
            throw new ChatEscalationStatusAlreadyExistsApplicationException(status.name());
        }

        return ChatEscalationStatusResponse.from(statusRepository.save(status));
    }
}