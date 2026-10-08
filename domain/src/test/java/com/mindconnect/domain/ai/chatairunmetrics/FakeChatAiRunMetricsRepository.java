package com.mindconnect.domain.ai.chatairunmetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunmetrics.port.repository.ChatAiRunMetricsRepository;

public class FakeChatAiRunMetricsRepository implements ChatAiRunMetricsRepository {

    private final ConcurrentMap<UUID, ChatAiRunMetrics> store = new ConcurrentHashMap<>();

    @Override
    public ChatAiRunMetrics save(ChatAiRunMetrics chatAiRunMetrics) {
        store.put(chatAiRunMetrics.id().value(), chatAiRunMetrics);
        return chatAiRunMetrics;
    }

    @Override
    public Optional<ChatAiRunMetrics> findById(ChatAiRunMetricsId id) {
        return Optional.ofNullable(store.get(id.value()));
    }

    @Override
    public Optional<ChatAiRunMetrics> findByAiRunId(ChatAiRunId aiRunId) {
        return store.values().stream()
                .filter(m -> m.aiRunId().equals(aiRunId))
                .findFirst();
    }

    @Override
    public List<ChatAiRunMetrics> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByAiRunId(ChatAiRunId aiRunId) {
        return store.values().stream()
                .anyMatch(m -> m.aiRunId().equals(aiRunId));
    }

    @Override
    public boolean existsByAiRunIdAndIdNot(ChatAiRunId aiRunId, ChatAiRunMetricsId id) {
        return store.values().stream()
                .anyMatch(m -> m.aiRunId().equals(aiRunId) && !m.id().equals(id));
    }

    @Override
    public void delete(ChatAiRunMetrics chatAiRunMetrics) {
        store.remove(chatAiRunMetrics.id().value());
    }
}