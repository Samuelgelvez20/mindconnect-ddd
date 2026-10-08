package com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunmetrics.port.repository.ChatAiRunMetricsRepository;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.entity.ChatAiRunMetricsJpaEntity;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.mappers.ChatAiRunMetricsPersistenceMapper;

public class ChatAiRunMetricsRepositoryAdapter implements ChatAiRunMetricsRepository {

    private final ChatAiRunMetricsJpaRepository jpaRepository;
    private final ChatAiRunMetricsPersistenceMapper mapper;

    public ChatAiRunMetricsRepositoryAdapter(
            ChatAiRunMetricsJpaRepository jpaRepository,
            ChatAiRunMetricsPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatAiRunMetrics save(ChatAiRunMetrics chatAiRunMetrics) {
        ChatAiRunMetricsJpaEntity entity = mapper.toJpa(chatAiRunMetrics);
        ChatAiRunMetricsJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatAiRunMetrics> findById(ChatAiRunMetricsId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<ChatAiRunMetrics> findByAiRunId(ChatAiRunId aiRunId) {
        return jpaRepository.findByAiRunId(aiRunId.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<ChatAiRunMetrics> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByAiRunId(ChatAiRunId aiRunId) {
        return jpaRepository.existsByAiRunId(aiRunId.value());
    }

    @Override
    public boolean existsByAiRunIdAndIdNot(ChatAiRunId aiRunId, ChatAiRunMetricsId id) {
        return jpaRepository.existsByAiRunIdAndIdNot(aiRunId.value(), id.value());
    }

    @Override
    public void delete(ChatAiRunMetrics chatAiRunMetrics) {
        jpaRepository.deleteById(chatAiRunMetrics.id().value());
    }
}