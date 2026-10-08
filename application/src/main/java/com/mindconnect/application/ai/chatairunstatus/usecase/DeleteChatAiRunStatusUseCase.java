package com.mindconnect.application.ai.chatairunstatus.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairunstatus.exception.ChatAiRunStatusNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.ai.chatairunstatus.port.repository.ChatAiRunStatusRepository;

public class DeleteChatAiRunStatusUseCase {

    private final ChatAiRunStatusRepository repository;

    public DeleteChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        ChatAiRunStatusId statusId = new ChatAiRunStatusId(id);
        ChatAiRunStatus status = repository.findById(statusId)
                .orElseThrow(() -> new ChatAiRunStatusNotFoundApplicationException(id.toString()));

        status.delete();
        repository.delete(status);
    }
}