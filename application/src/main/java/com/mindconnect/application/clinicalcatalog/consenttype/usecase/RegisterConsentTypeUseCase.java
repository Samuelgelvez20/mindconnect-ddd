package com.mindconnect.application.clinicalcatalog.consenttype.usecase;

import com.mindconnect.application.clinicalcatalog.consenttype.dto.ConsentTypeResponse;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;
import com.mindconnect.application.clinicalcatalog.consenttype.command.RegisterConsentTypeCommand;
import com.mindconnect.application.clinicalcatalog.consenttype.exception.ConsentTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.consenttype.port.repository.ConsentTypeRepository;

import java.util.Optional;

public class RegisterConsentTypeUseCase {

    private final ConsentTypeRepository repository;

    public RegisterConsentTypeUseCase(ConsentTypeRepository repository) {
        this.repository = repository;
    }

    public ConsentTypeResponse execute(RegisterConsentTypeCommand command) {
        Optional<ConsentType> existing = repository.findByCode(command.code());

        if (existing.isPresent()) {
            throw new ConsentTypeAlreadyExistsApplicationException(command.code());
        }

        ConsentType type = ConsentType.register(command.code(), command.name(), command.description());
        repository.save(type);

        return ConsentTypeResponse.from(type);
    }
}