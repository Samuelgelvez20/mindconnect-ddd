package com.mindconnect.application.ai.chatairunstatus.usecase;

import java.util.List;

import com.mindconnect.application.ai.chatairunstatus.dto.ChatAiRunStatusResponse;
import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.port.repository.ChatAiRunStatusRepository;

public class ListChatAiRunStatusUseCase {

    private final ChatAiRunStatusRepository repository;

    public ListChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        this.repository = repository;
    }

    public List<ChatAiRunStatusResponse> execute() {
        List<ChatAiRunStatus> statuses = repository.findAll();
        return statuses.stream()
                .map(ChatAiRunStatusResponse::from)
                .toList();
    }
}