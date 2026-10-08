package com.mindconnect.application.chat.chatescalation.usecase;

import com.mindconnect.application.chat.chatescalation.command.UpdateChatEscalationCommand;
import com.mindconnect.application.chat.chatescalation.dto.ChatEscalationResponse;
import com.mindconnect.application.chat.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalation.port.repository.ChatEscalationRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class UpdateChatEscalationUseCase {

    private final ChatEscalationRepository escalationRepository;

    public UpdateChatEscalationUseCase(ChatEscalationRepository escalationRepository) {
        this.escalationRepository = escalationRepository;
    }

    public ChatEscalationResponse execute(UpdateChatEscalationCommand command) {

        var escalation = escalationRepository.findById(command.id())
                .orElseThrow(() -> new ChatEscalationNotFoundApplicationException(command.id()));

        escalation.update(
                command.conversationId(),
                command.statusId(),
                command.fromAi(),
                command.reason());

        return ChatEscalationResponse.from(escalationRepository.save(escalation));
    }
}