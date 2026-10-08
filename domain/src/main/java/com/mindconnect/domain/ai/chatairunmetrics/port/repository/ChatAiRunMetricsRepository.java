package com.mindconnect.domain.ai.chatairunmetrics.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

public interface ChatAiRunMetricsRepository {

    ChatAiRunMetrics save(ChatAiRunMetrics chatAiRunMetrics);

    Optional<ChatAiRunMetrics> findById(ChatAiRunMetricsId id);

    Optional<ChatAiRunMetrics> findByAiRunId(ChatAiRunId aiRunId);

    List<ChatAiRunMetrics> findAll();

    boolean existsByAiRunId(ChatAiRunId aiRunId);

    boolean existsByAiRunIdAndIdNot(ChatAiRunId aiRunId, ChatAiRunMetricsId id);

    void delete(ChatAiRunMetrics chatAiRunMetrics);
}