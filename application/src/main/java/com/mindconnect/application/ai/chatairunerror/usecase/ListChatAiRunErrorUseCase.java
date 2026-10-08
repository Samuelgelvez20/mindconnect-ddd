package com.mindconnect.application.ai.chatairunerror.usecase;

import java.util.List;

import com.mindconnect.application.ai.chatairunerror.dto.ChatAiRunErrorResponse;
import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;

public class ListChatAiRunErrorUseCase {

    private final ChatAiRunErrorRepository repository;

    public ListChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        this.repository = repository;
    }

    public List<ChatAiRunErrorResponse> execute() {
        List<ChatAiRunError> errors = repository.findAll();
        return errors.stream()
                .map(ChatAiRunErrorResponse::from)
                .toList();
    }
}