package com.mindconnect.application.chat.chatconversationstatus.usecase;

import com.mindconnect.application.chat.chatconversationstatus.command.RegisterChatConversationStatusCommand;
import com.mindconnect.application.chat.chatconversationstatus.dto.ChatConversationStatusResponse;
import com.mindconnect.application.chat.chatconversationstatus.exception.ChatConversationStatusAlreadyExistsApplicationException;
import com.mindconnect.domain.chat.chatconversationstatus.model.aggregate.ChatConversationStatus;
import com.mindconnect.domain.chat.chatconversationstatus.port.repository.ChatConversationStatusRepository;

public class RegisterChatConversationStatusUseCase {

    private final ChatConversationStatusRepository statusRepository;

    public RegisterChatConversationStatusUseCase(ChatConversationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ChatConversationStatusResponse execute(RegisterChatConversationStatusCommand command) {

        ChatConversationStatus status = ChatConversationStatus.register(command.name());

        if (statusRepository.existsByName(status.name())) {
            throw new ChatConversationStatusAlreadyExistsApplicationException(status.name());
        }

        return ChatConversationStatusResponse.from(statusRepository.save(status));
    }
}