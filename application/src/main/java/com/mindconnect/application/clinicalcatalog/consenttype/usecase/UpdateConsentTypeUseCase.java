package com.mindconnect.application.clinicalcatalog.consenttype.usecase;

import com.mindconnect.application.clinicalcatalog.consenttype.dto.ConsentTypeResponse;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;
import com.mindconnect.application.clinicalcatalog.consenttype.command.UpdateConsentTypeCommand;
import com.mindconnect.application.clinicalcatalog.consenttype.exception.ConsentTypeNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.consenttype.exception.ConsentTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.consenttype.port.repository.ConsentTypeRepository;

public class UpdateConsentTypeUseCase {

    private final ConsentTypeRepository repository;

    public UpdateConsentTypeUseCase(ConsentTypeRepository repository) {
        this.repository = repository;
    }

    public ConsentTypeResponse execute(ConsentTypeId id, UpdateConsentTypeCommand command) {
        ConsentType type = repository.findById(id)
                .orElseThrow(() -> new ConsentTypeNotFoundApplicationException(id.value().toString()));

        if (repository.existsByCodeAndIdNot(command.code(), id)) {
            throw new ConsentTypeAlreadyExistsApplicationException(command.code());
        }

        type.update(command.code(), command.name(), command.description(), command.active());

        repository.save(type);

        return ConsentTypeResponse.from(type);
    }
}