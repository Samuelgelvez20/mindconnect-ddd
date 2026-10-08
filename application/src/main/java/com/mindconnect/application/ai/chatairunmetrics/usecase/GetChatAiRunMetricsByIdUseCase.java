package com.mindconnect.application.ai.chatairunmetrics.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairunmetrics.dto.ChatAiRunMetricsResponse;
import com.mindconnect.application.ai.chatairunmetrics.exception.ChatAiRunMetricsNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairunmetrics.port.repository.ChatAiRunMetricsRepository;

public class GetChatAiRunMetricsByIdUseCase {

    private final ChatAiRunMetricsRepository repository;

    public GetChatAiRunMetricsByIdUseCase(ChatAiRunMetricsRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunMetricsResponse execute(UUID id) {
        ChatAiRunMetricsId metricsId = new ChatAiRunMetricsId(id);
        ChatAiRunMetrics metrics = repository.findById(metricsId)
                .orElseThrow(() -> new ChatAiRunMetricsNotFoundApplicationException(id.toString()));
        return ChatAiRunMetricsResponse.from(metrics);
    }
}