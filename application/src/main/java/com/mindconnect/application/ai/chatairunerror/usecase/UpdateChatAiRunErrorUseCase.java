package com.mindconnect.application.ai.chatairunerror.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairunerror.command.UpdateChatAiRunErrorCommand;
import com.mindconnect.application.ai.chatairunerror.dto.ChatAiRunErrorResponse;
import com.mindconnect.application.ai.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;

public class UpdateChatAiRunErrorUseCase {

    private final ChatAiRunErrorRepository repository;

    public UpdateChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunErrorResponse execute(UpdateChatAiRunErrorCommand command) {
        ChatAiRunErrorId errorId = new ChatAiRunErrorId(command.id());
        ChatAiRunError error = repository.findById(errorId)
                .orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(command.id().toString()));

        ChatAiRunId aiRunId = new ChatAiRunId(command.aiRunId());
        error.update(aiRunId, command.errorMessage(), command.errorCode(), command.providerErrorId());

        ChatAiRunError saved = repository.save(error);
        return ChatAiRunErrorResponse.from(saved);
    }
}