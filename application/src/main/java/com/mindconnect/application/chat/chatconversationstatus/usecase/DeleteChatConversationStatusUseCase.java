package com.mindconnect.application.chat.chatconversationstatus.usecase;

import com.mindconnect.application.chat.chatconversationstatus.exception.ChatConversationStatusNotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.chatconversationstatus.port.repository.ChatConversationStatusRepository;

public class DeleteChatConversationStatusUseCase {

    private final ChatConversationStatusRepository statusRepository;

    public DeleteChatConversationStatusUseCase(ChatConversationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void execute(ChatConversationStatusId id) {

        var status = statusRepository.findById(id)
                .orElseThrow(() -> new ChatConversationStatusNotFoundApplicationException(id));

        status.delete();
        statusRepository.delete(status);
    }
}