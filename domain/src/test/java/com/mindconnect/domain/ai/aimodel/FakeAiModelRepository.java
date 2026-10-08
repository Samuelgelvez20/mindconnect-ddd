package com.mindconnect.domain.ai.aimodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;

public class FakeAiModelRepository implements AiModelRepository {

    private final ConcurrentMap<UUID, AiModel> store = new ConcurrentHashMap<>();

    @Override
    public AiModel save(AiModel aiModel) {
        store.put(aiModel.id().value(), aiModel);
        return aiModel;
    }

    @Override
    public Optional<AiModel> findById(AiModelId id) {
        return Optional.ofNullable(store.get(id.value()));
    }

    @Override
    public List<AiModel> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByModelKey(String modelKey) {
        return store.values().stream()
                .anyMatch(m -> m.modelKey().equals(modelKey));
    }

    @Override
    public boolean existsByModelKeyAndIdNot(String modelKey, AiModelId id) {
        return store.values().stream()
                .anyMatch(m -> m.modelKey().equals(modelKey) && !m.id().equals(id));
    }

    @Override
    public void delete(AiModel aiModel) {
        store.remove(aiModel.id().value());
    }
}