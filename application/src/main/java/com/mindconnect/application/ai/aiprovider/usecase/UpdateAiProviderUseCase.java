package com.mindconnect.application.ai.aiprovider.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.aiprovider.command.UpdateAiProviderCommand;
import com.mindconnect.application.ai.aiprovider.dto.AiProviderResponse;
import com.mindconnect.application.ai.aiprovider.exception.AiProviderAlreadyExistsApplicationException;
import com.mindconnect.application.ai.aiprovider.exception.AiProviderNotFoundApplicationException;
import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;

public class UpdateAiProviderUseCase {

    private final AiProviderRepository repository;

    public UpdateAiProviderUseCase(AiProviderRepository repository) {
        this.repository = repository;
    }

    public AiProviderResponse execute(UpdateAiProviderCommand command) {
        AiProviderId providerId = new AiProviderId(command.id());
        AiProvider provider = repository.findById(providerId)
                .orElseThrow(() -> new AiProviderNotFoundApplicationException(command.id().toString()));

        if (repository.existsByNameAndIdNot(command.name(), providerId)) {
            throw new AiProviderAlreadyExistsApplicationException(command.name());
        }

        provider.update(
                command.name(),
                command.legalName(),
                command.website(),
                command.active());

        AiProvider saved = repository.save(provider);
        return AiProviderResponse.from(saved);
    }
}