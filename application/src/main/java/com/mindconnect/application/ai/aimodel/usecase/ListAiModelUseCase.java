package com.mindconnect.application.ai.aimodel.usecase;

import java.util.List;

import com.mindconnect.application.ai.aimodel.dto.AiModelResponse;
import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;

public class ListAiModelUseCase {

    private final AiModelRepository repository;

    public ListAiModelUseCase(AiModelRepository repository) {
        this.repository = repository;
    }

    public List<AiModelResponse> execute() {
        List<AiModel> models = repository.findAll();
        return models.stream()
                .map(AiModelResponse::from)
                .toList();
    }
}