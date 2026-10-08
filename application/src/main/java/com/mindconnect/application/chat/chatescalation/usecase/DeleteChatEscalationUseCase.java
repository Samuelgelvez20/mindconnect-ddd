package com.mindconnect.application.chat.chatescalation.usecase;

import com.mindconnect.application.chat.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalation.port.repository.ChatEscalationRepository;

public class DeleteChatEscalationUseCase {

    private final ChatEscalationRepository escalationRepository;

    public DeleteChatEscalationUseCase(ChatEscalationRepository escalationRepository) {
        this.escalationRepository = escalationRepository;
    }

    public void execute(ChatEscalationId id) {

        var escalation = escalationRepository.findById(id)
                .orElseThrow(() -> new ChatEscalationNotFoundApplicationException(id));

        escalation.delete();
        escalationRepository.delete(escalation);
    }
}