package com.mindconnect.application.clinicalrecord.encounter.usecase;

import com.mindconnect.application.clinicalrecord.encounter.exception.EncounterNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.encounter.port.repository.EncounterRepository;

public class DeleteEncounterUseCase {

    private final EncounterRepository repository;

    public DeleteEncounterUseCase(EncounterRepository repository) {
        this.repository = repository;
    }

    public void execute(EncounterId id) {

        var encounter = repository.findById(id)
                .orElseThrow(() -> new EncounterNotFoundApplicationException(id));

        encounter.delete();
        repository.delete(encounter);
    }
}