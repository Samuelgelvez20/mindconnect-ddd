package com.mindconnect.application.ai.chatairun.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;

public class DeleteChatAiRunUseCase {

    private final ChatAiRunRepository repository;

    public DeleteChatAiRunUseCase(ChatAiRunRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        ChatAiRunId runId = new ChatAiRunId(id);
        ChatAiRun run = repository.findById(runId)
                .orElseThrow(() -> new ChatAiRunNotFoundApplicationException(id.toString()));

        run.delete();
        repository.delete(run);
    }
}