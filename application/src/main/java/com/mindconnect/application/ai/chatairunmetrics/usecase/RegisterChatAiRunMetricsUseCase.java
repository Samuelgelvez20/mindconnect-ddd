package com.mindconnect.application.ai.chatairunmetrics.usecase;

import java.math.BigDecimal;
import java.util.UUID;

import com.mindconnect.application.ai.chatairunmetrics.command.RegisterChatAiRunMetricsCommand;
import com.mindconnect.application.ai.chatairunmetrics.dto.ChatAiRunMetricsResponse;
import com.mindconnect.application.ai.chatairunmetrics.exception.ChatAiRunMetricsAlreadyExistsApplicationException;
import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunmetrics.port.repository.ChatAiRunMetricsRepository;

public class RegisterChatAiRunMetricsUseCase {

    private final ChatAiRunMetricsRepository repository;

    public RegisterChatAiRunMetricsUseCase(ChatAiRunMetricsRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunMetricsResponse execute(RegisterChatAiRunMetricsCommand command) {
        ChatAiRunId aiRunId = new ChatAiRunId(command.aiRunId());
        if (repository.existsByAiRunId(aiRunId)) {
            throw new ChatAiRunMetricsAlreadyExistsApplicationException(command.aiRunId().toString());
        }

        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(
                aiRunId,
                command.promptTokens(),
                command.completionTokens(),
                command.totalTokens(),
                command.cost());

        ChatAiRunMetrics saved = repository.save(metrics);
        return ChatAiRunMetricsResponse.from(saved);
    }
}