package com.mindconnect.domain.ai.aimodel.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;

public interface AiModelRepository {

    AiModel save(AiModel aiModel);

    Optional<AiModel> findById(AiModelId id);

    List<AiModel> findAll();

    boolean existsByModelKey(String modelKey);

    boolean existsByModelKeyAndIdNot(String modelKey, AiModelId id);

    void delete(AiModel aiModel);
}