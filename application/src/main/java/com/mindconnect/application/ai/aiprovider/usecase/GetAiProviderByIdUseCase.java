package com.mindconnect.application.ai.aiprovider.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.aiprovider.dto.AiProviderResponse;
import com.mindconnect.application.ai.aiprovider.exception.AiProviderNotFoundApplicationException;
import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;

public class GetAiProviderByIdUseCase {

    private final AiProviderRepository repository;

    public GetAiProviderByIdUseCase(AiProviderRepository repository) {
        this.repository = repository;
    }

    public AiProviderResponse execute(UUID id) {
        AiProviderId providerId = new AiProviderId(id);
        AiProvider provider = repository.findById(providerId)
                .orElseThrow(() -> new AiProviderNotFoundApplicationException(id.toString()));
        return AiProviderResponse.from(provider);
    }
}