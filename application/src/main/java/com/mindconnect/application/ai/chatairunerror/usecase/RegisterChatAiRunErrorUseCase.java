package com.mindconnect.application.ai.chatairunerror.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairunerror.command.RegisterChatAiRunErrorCommand;
import com.mindconnect.application.ai.chatairunerror.dto.ChatAiRunErrorResponse;
import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;

public class RegisterChatAiRunErrorUseCase {

    private final ChatAiRunErrorRepository repository;

    public RegisterChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunErrorResponse execute(RegisterChatAiRunErrorCommand command) {
        ChatAiRunId aiRunId = new ChatAiRunId(command.aiRunId());

        ChatAiRunError error = ChatAiRunError.register(
                aiRunId,
                command.errorMessage(),
                command.errorCode(),
                command.providerErrorId());

        ChatAiRunError saved = repository.save(error);
        return ChatAiRunErrorResponse.from(saved);
    }
}