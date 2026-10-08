package com.mindconnect.application.ai.chatairunmetrics.usecase;

import java.math.BigDecimal;
import java.util.UUID;

import com.mindconnect.application.ai.chatairunmetrics.command.UpdateChatAiRunMetricsCommand;
import com.mindconnect.application.ai.chatairunmetrics.dto.ChatAiRunMetricsResponse;
import com.mindconnect.application.ai.chatairunmetrics.exception.ChatAiRunMetricsAlreadyExistsApplicationException;
import com.mindconnect.application.ai.chatairunmetrics.exception.ChatAiRunMetricsNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunmetrics.port.repository.ChatAiRunMetricsRepository;

public class UpdateChatAiRunMetricsUseCase {

    private final ChatAiRunMetricsRepository repository;

    public UpdateChatAiRunMetricsUseCase(ChatAiRunMetricsRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunMetricsResponse execute(UpdateChatAiRunMetricsCommand command) {
        ChatAiRunMetricsId metricsId = new ChatAiRunMetricsId(command.id());
        ChatAiRunMetrics metrics = repository.findById(metricsId)
                .orElseThrow(() -> new ChatAiRunMetricsNotFoundApplicationException(command.id().toString()));

        ChatAiRunId aiRunId = new ChatAiRunId(command.aiRunId());
        if (repository.existsByAiRunIdAndIdNot(aiRunId, metricsId)) {
            throw new ChatAiRunMetricsAlreadyExistsApplicationException(command.aiRunId().toString());
        }

        metrics.update(aiRunId, command.promptTokens(), command.completionTokens(), command.totalTokens(), command.cost());

        ChatAiRunMetrics saved = repository.save(metrics);
        return ChatAiRunMetricsResponse.from(saved);
    }
}