package com.mindconnect.application.ai.chatairunerror.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;

public class DeleteChatAiRunErrorUseCase {

    private final ChatAiRunErrorRepository repository;

    public DeleteChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        ChatAiRunErrorId errorId = new ChatAiRunErrorId(id);
        ChatAiRunError error = repository.findById(errorId)
                .orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(id.toString()));

        error.delete();
        repository.delete(error);
    }
}