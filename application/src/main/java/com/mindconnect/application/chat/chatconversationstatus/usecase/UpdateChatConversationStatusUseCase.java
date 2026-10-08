package com.mindconnect.application.chat.chatconversationstatus.usecase;

import com.mindconnect.application.chat.chatconversationstatus.command.UpdateChatConversationStatusCommand;
import com.mindconnect.application.chat.chatconversationstatus.dto.ChatConversationStatusResponse;
import com.mindconnect.application.chat.chatconversationstatus.exception.ChatConversationStatusAlreadyExistsApplicationException;
import com.mindconnect.application.chat.chatconversationstatus.exception.ChatConversationStatusNotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.chatconversationstatus.port.repository.ChatConversationStatusRepository;

public class UpdateChatConversationStatusUseCase {

    private final ChatConversationStatusRepository statusRepository;

    public UpdateChatConversationStatusUseCase(ChatConversationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ChatConversationStatusResponse execute(UpdateChatConversationStatusCommand command) {

        var status = statusRepository.findById(command.id())
                .orElseThrow(() -> new ChatConversationStatusNotFoundApplicationException(command.id()));

        status.update(command.name());

        if (statusRepository.existsByNameAndIdNot(status.name(), status.id())) {
            throw new ChatConversationStatusAlreadyExistsApplicationException(status.name());
        }

        return ChatConversationStatusResponse.from(statusRepository.save(status));
    }
}