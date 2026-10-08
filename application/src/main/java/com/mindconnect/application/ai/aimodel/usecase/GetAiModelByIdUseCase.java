package com.mindconnect.application.ai.aimodel.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.aimodel.dto.AiModelResponse;
import com.mindconnect.application.ai.aimodel.exception.AiModelNotFoundApplicationException;
import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;

public class GetAiModelByIdUseCase {

    private final AiModelRepository repository;

    public GetAiModelByIdUseCase(AiModelRepository repository) {
        this.repository = repository;
    }

    public AiModelResponse execute(UUID id) {
        AiModelId modelId = new AiModelId(id);
        AiModel model = repository.findById(modelId)
                .orElseThrow(() -> new AiModelNotFoundApplicationException(id.toString()));
        return AiModelResponse.from(model);
    }
}