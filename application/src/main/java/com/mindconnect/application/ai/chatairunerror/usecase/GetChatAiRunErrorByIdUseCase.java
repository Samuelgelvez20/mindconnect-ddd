package com.mindconnect.application.ai.chatairunerror.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairunerror.dto.ChatAiRunErrorResponse;
import com.mindconnect.application.ai.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;

public class GetChatAiRunErrorByIdUseCase {

    private final ChatAiRunErrorRepository repository;

    public GetChatAiRunErrorByIdUseCase(ChatAiRunErrorRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunErrorResponse execute(UUID id) {
        ChatAiRunErrorId errorId = new ChatAiRunErrorId(id);
        ChatAiRunError error = repository.findById(errorId)
                .orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(id.toString()));
        return ChatAiRunErrorResponse.from(error);
    }
}