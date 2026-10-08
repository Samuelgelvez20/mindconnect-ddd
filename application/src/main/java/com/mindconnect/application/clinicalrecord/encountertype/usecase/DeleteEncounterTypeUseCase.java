package com.mindconnect.application.clinicalrecord.encountertype.usecase;

import com.mindconnect.application.clinicalrecord.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountertype.port.repository.EncounterTypeRepository;

public class DeleteEncounterTypeUseCase {

    private final EncounterTypeRepository repository;

    public DeleteEncounterTypeUseCase(EncounterTypeRepository repository) {
        this.repository = repository;
    }

    public void execute(EncounterTypeId id) {

        var type = repository.findById(id)
                .orElseThrow(() -> new EncounterTypeNotFoundApplicationException(id));

        type.delete();
        repository.delete(type);
    }
}