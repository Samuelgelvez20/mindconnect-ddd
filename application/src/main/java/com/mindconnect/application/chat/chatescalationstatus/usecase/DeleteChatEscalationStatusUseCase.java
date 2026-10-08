package com.mindconnect.application.chat.chatescalationstatus.usecase;

import com.mindconnect.application.chat.chatescalationstatus.exception.ChatEscalationStatusNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.domain.chat.chatescalationstatus.port.repository.ChatEscalationStatusRepository;

public class DeleteChatEscalationStatusUseCase {

    private final ChatEscalationStatusRepository statusRepository;

    public DeleteChatEscalationStatusUseCase(ChatEscalationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void execute(ChatEscalationStatusId id) {

        var status = statusRepository.findById(id)
                .orElseThrow(() -> new ChatEscalationStatusNotFoundApplicationException(id));

        status.delete();
        statusRepository.delete(status);
    }
}