package com.mindconnect.application.clinicalrecord.encountertype.usecase;

import com.mindconnect.application.clinicalrecord.encountertype.dto.EncounterTypeResponse;
import com.mindconnect.application.clinicalrecord.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountertype.port.repository.EncounterTypeRepository;

public class GetEncounterTypeByIdUseCase {

    private final EncounterTypeRepository repository;

    public GetEncounterTypeByIdUseCase(EncounterTypeRepository repository) {
        this.repository = repository;
    }

    public EncounterTypeResponse execute(EncounterTypeId id) {
        return repository.findById(id)
                .map(EncounterTypeResponse::from)
                .orElseThrow(() -> new EncounterTypeNotFoundApplicationException(id));
    }
}