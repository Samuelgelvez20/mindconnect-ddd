package com.mindconnect.application.ai.chatairun.usecase;

import java.util.List;

import com.mindconnect.application.ai.chatairun.dto.ChatAiRunResponse;
import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;

public class ListChatAiRunUseCase {

    private final ChatAiRunRepository repository;

    public ListChatAiRunUseCase(ChatAiRunRepository repository) {
        this.repository = repository;
    }

    public List<ChatAiRunResponse> execute() {
        List<ChatAiRun> runs = repository.findAll();
        return runs.stream()
                .map(ChatAiRunResponse::from)
                .toList();
    }
}