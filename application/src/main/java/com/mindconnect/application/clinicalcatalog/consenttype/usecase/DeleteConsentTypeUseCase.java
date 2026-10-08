package com.mindconnect.application.clinicalcatalog.consenttype.usecase;

import com.mindconnect.application.clinicalcatalog.consenttype.exception.ConsentTypeNotFoundApplicationException;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;
import com.mindconnect.domain.clinicalcatalog.consenttype.port.repository.ConsentTypeRepository;

public class DeleteConsentTypeUseCase {

    private final ConsentTypeRepository repository;

    public DeleteConsentTypeUseCase(ConsentTypeRepository repository) {
        this.repository = repository;
    }

    public void execute(ConsentTypeId id) {
        ConsentType type = repository.findById(id)
                .orElseThrow(() -> new ConsentTypeNotFoundApplicationException(id.value().toString()));

        repository.delete(type);
    }
}