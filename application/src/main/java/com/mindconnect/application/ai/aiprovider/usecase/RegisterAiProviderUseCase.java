package com.mindconnect.application.ai.aiprovider.usecase;

import com.mindconnect.application.ai.aiprovider.command.RegisterAiProviderCommand;
import com.mindconnect.application.ai.aiprovider.dto.AiProviderResponse;
import com.mindconnect.application.ai.aiprovider.exception.AiProviderAlreadyExistsApplicationException;
import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;

public class RegisterAiProviderUseCase {

    private final AiProviderRepository repository;

    public RegisterAiProviderUseCase(AiProviderRepository repository) {
        this.repository = repository;
    }

    public AiProviderResponse execute(RegisterAiProviderCommand command) {
        if (repository.existsByName(command.name())) {
            throw new AiProviderAlreadyExistsApplicationException(command.name());
        }

        AiProvider provider = AiProvider.register(
                command.name(),
                command.legalName(),
                command.website());

        AiProvider saved = repository.save(provider);
        return AiProviderResponse.from(saved);
    }
}