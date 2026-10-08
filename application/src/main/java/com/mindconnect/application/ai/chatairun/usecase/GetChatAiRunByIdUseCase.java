package com.mindconnect.application.ai.chatairun.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairun.dto.ChatAiRunResponse;
import com.mindconnect.application.ai.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;

public class GetChatAiRunByIdUseCase {

    private final ChatAiRunRepository repository;

    public GetChatAiRunByIdUseCase(ChatAiRunRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunResponse execute(UUID id) {
        ChatAiRunId runId = new ChatAiRunId(id);
        ChatAiRun run = repository.findById(runId)
                .orElseThrow(() -> new ChatAiRunNotFoundApplicationException(id.toString()));
        return ChatAiRunResponse.from(run);
    }
}