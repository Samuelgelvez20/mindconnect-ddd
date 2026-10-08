package com.mindconnect.application.chat.chatescalation.usecase;

import com.mindconnect.application.chat.chatescalation.command.RegisterChatEscalationCommand;
import com.mindconnect.application.chat.chatescalation.dto.ChatEscalationResponse;
import com.mindconnect.domain.chat.chatescalation.model.aggregate.ChatEscalation;
import com.mindconnect.domain.chat.chatescalation.port.repository.ChatEscalationRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class RegisterChatEscalationUseCase {

    private final ChatEscalationRepository escalationRepository;

    public RegisterChatEscalationUseCase(ChatEscalationRepository escalationRepository) {
        this.escalationRepository = escalationRepository;
    }

    public ChatEscalationResponse execute(RegisterChatEscalationCommand command) {

        ChatEscalation escalation = ChatEscalation.register(
                command.conversationId(),
                command.statusId(),
                command.fromAi(),
                command.reason());

        return ChatEscalationResponse.from(escalationRepository.save(escalation));
    }
}