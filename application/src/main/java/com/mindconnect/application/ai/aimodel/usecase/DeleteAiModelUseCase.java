package com.mindconnect.application.ai.aimodel.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.aimodel.exception.AiModelNotFoundApplicationException;
import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;

public class DeleteAiModelUseCase {

    private final AiModelRepository repository;

    public DeleteAiModelUseCase(AiModelRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        AiModelId modelId = new AiModelId(id);
        AiModel model = repository.findById(modelId)
                .orElseThrow(() -> new AiModelNotFoundApplicationException(id.toString()));

        model.delete();
        repository.delete(model);
    }
}