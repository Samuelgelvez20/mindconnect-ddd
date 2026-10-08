package com.mindconnect.application.ai.aiprovider.usecase;

import java.util.List;

import com.mindconnect.application.ai.aiprovider.dto.AiProviderResponse;
import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;

public class ListAiProviderUseCase {

    private final AiProviderRepository repository;

    public ListAiProviderUseCase(AiProviderRepository repository) {
        this.repository = repository;
    }

    public List<AiProviderResponse> execute() {
        List<AiProvider> providers = repository.findAll();
        return providers.stream()
                .map(AiProviderResponse::from)
                .toList();
    }
}