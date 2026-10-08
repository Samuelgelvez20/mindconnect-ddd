package com.mindconnect.application.ai.aiprovider.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.aiprovider.exception.AiProviderNotFoundApplicationException;
import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;

public class DeleteAiProviderUseCase {

    private final AiProviderRepository repository;

    public DeleteAiProviderUseCase(AiProviderRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        AiProviderId providerId = new AiProviderId(id);
        AiProvider provider = repository.findById(providerId)
                .orElseThrow(() -> new AiProviderNotFoundApplicationException(id.toString()));

        provider.delete();
        repository.delete(provider);
    }
}