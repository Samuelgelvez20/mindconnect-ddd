package com.mindconnect.application.ai.chatairunstatus.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairunstatus.command.UpdateChatAiRunStatusCommand;
import com.mindconnect.application.ai.chatairunstatus.dto.ChatAiRunStatusResponse;
import com.mindconnect.application.ai.chatairunstatus.exception.ChatAiRunStatusAlreadyExistsApplicationException;
import com.mindconnect.application.ai.chatairunstatus.exception.ChatAiRunStatusNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.ai.chatairunstatus.port.repository.ChatAiRunStatusRepository;

public class UpdateChatAiRunStatusUseCase {

    private final ChatAiRunStatusRepository repository;

    public UpdateChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunStatusResponse execute(UpdateChatAiRunStatusCommand command) {
        ChatAiRunStatusId statusId = new ChatAiRunStatusId(command.id());
        ChatAiRunStatus status = repository.findById(statusId)
                .orElseThrow(() -> new ChatAiRunStatusNotFoundApplicationException(command.id().toString()));

        if (repository.existsByNameAndIdNot(command.name(), statusId)) {
            throw new ChatAiRunStatusAlreadyExistsApplicationException(command.name());
        }

        status.update(command.name());

        ChatAiRunStatus saved = repository.save(status);
        return ChatAiRunStatusResponse.from(saved);
    }
}