package com.mindconnect.application.ai.chatairunmetrics.usecase;

import java.util.List;

import com.mindconnect.application.ai.chatairunmetrics.dto.ChatAiRunMetricsResponse;
import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.port.repository.ChatAiRunMetricsRepository;

public class ListChatAiRunMetricsUseCase {

    private final ChatAiRunMetricsRepository repository;

    public ListChatAiRunMetricsUseCase(ChatAiRunMetricsRepository repository) {
        this.repository = repository;
    }

    public List<ChatAiRunMetricsResponse> execute() {
        List<ChatAiRunMetrics> metrics = repository.findAll();
        return metrics.stream()
                .map(ChatAiRunMetricsResponse::from)
                .toList();
    }
}