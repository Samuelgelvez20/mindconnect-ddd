package com.mindconnect.application.ai.chatairunstatus.usecase;

import com.mindconnect.application.ai.chatairunstatus.command.RegisterChatAiRunStatusCommand;
import com.mindconnect.application.ai.chatairunstatus.dto.ChatAiRunStatusResponse;
import com.mindconnect.application.ai.chatairunstatus.exception.ChatAiRunStatusAlreadyExistsApplicationException;
import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.ai.chatairunstatus.port.repository.ChatAiRunStatusRepository;

public class RegisterChatAiRunStatusUseCase {

    private final ChatAiRunStatusRepository repository;

    public RegisterChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunStatusResponse execute(RegisterChatAiRunStatusCommand command) {
        if (repository.existsByName(command.name())) {
            throw new ChatAiRunStatusAlreadyExistsApplicationException(command.name());
        }

        ChatAiRunStatus status = ChatAiRunStatus.register(command.name());

        ChatAiRunStatus saved = repository.save(status);
        return ChatAiRunStatusResponse.from(saved);
    }
}