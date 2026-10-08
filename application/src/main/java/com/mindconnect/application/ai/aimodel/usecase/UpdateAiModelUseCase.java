package com.mindconnect.application.ai.aimodel.usecase;

import java.math.BigDecimal;
import java.util.UUID;

import com.mindconnect.application.ai.aimodel.command.UpdateAiModelCommand;
import com.mindconnect.application.ai.aimodel.dto.AiModelResponse;
import com.mindconnect.application.ai.aimodel.exception.AiModelAlreadyExistsApplicationException;
import com.mindconnect.application.ai.aimodel.exception.AiModelNotFoundApplicationException;
import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;

public class UpdateAiModelUseCase {

    private final AiModelRepository repository;

    public UpdateAiModelUseCase(AiModelRepository repository) {
        this.repository = repository;
    }

    public AiModelResponse execute(UpdateAiModelCommand command) {
        AiModelId modelId = new AiModelId(command.id());
        AiModel model = repository.findById(modelId)
                .orElseThrow(() -> new AiModelNotFoundApplicationException(command.id().toString()));

        if (repository.existsByModelKeyAndIdNot(command.modelKey(), modelId)) {
            throw new AiModelAlreadyExistsApplicationException(command.modelKey());
        }

        AiProviderId providerId = new AiProviderId(command.aiProviderId());

        model.update(
                providerId,
                command.name(),
                command.modelKey(),
                command.inputTokenPrice(),
                command.outputTokenPrice(),
                command.maxTokens(),
                command.contextWindow(),
                command.active());

        AiModel saved = repository.save(model);
        return AiModelResponse.from(saved);
    }
}