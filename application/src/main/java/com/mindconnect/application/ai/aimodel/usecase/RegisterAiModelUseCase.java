package com.mindconnect.application.ai.aimodel.usecase;

import java.math.BigDecimal;
import java.util.UUID;

import com.mindconnect.application.ai.aimodel.command.RegisterAiModelCommand;
import com.mindconnect.application.ai.aimodel.dto.AiModelResponse;
import com.mindconnect.application.ai.aimodel.exception.AiModelAlreadyExistsApplicationException;
import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;

public class RegisterAiModelUseCase {

    private final AiModelRepository repository;

    public RegisterAiModelUseCase(AiModelRepository repository) {
        this.repository = repository;
    }

    public AiModelResponse execute(RegisterAiModelCommand command) {
        if (repository.existsByModelKey(command.modelKey())) {
            throw new AiModelAlreadyExistsApplicationException(command.modelKey());
        }

        AiProviderId providerId = new AiProviderId(command.aiProviderId());

        AiModel model = AiModel.register(
                providerId,
                command.name(),
                command.modelKey(),
                command.inputTokenPrice(),
                command.outputTokenPrice(),
                command.maxTokens(),
                command.contextWindow());

        AiModel saved = repository.save(model);
        return AiModelResponse.from(saved);
    }
}