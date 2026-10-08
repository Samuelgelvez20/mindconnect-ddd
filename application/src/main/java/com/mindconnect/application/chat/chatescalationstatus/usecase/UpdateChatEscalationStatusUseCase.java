package com.mindconnect.application.chat.chatescalationstatus.usecase;

import com.mindconnect.application.chat.chatescalationstatus.command.UpdateChatEscalationStatusCommand;
import com.mindconnect.application.chat.chatescalationstatus.dto.ChatEscalationStatusResponse;
import com.mindconnect.application.chat.chatescalationstatus.exception.ChatEscalationStatusAlreadyExistsApplicationException;
import com.mindconnect.application.chat.chatescalationstatus.exception.ChatEscalationStatusNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.domain.chat.chatescalationstatus.port.repository.ChatEscalationStatusRepository;

public class UpdateChatEscalationStatusUseCase {

    private final ChatEscalationStatusRepository statusRepository;

    public UpdateChatEscalationStatusUseCase(ChatEscalationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ChatEscalationStatusResponse execute(UpdateChatEscalationStatusCommand command) {

        var status = statusRepository.findById(command.id())
                .orElseThrow(() -> new ChatEscalationStatusNotFoundApplicationException(command.id()));

        status.update(command.name());

        if (statusRepository.existsByNameAndIdNot(status.name(), status.id())) {
            throw new ChatEscalationStatusAlreadyExistsApplicationException(status.name());
        }

        return ChatEscalationStatusResponse.from(statusRepository.save(status));
    }
}