package com.mindconnect.application.clinicalrecord.encountermodality.usecase;

import com.mindconnect.application.clinicalrecord.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encountermodality.port.repository.EncounterModalityRepository;

public class DeleteEncounterModalityUseCase {

    private final EncounterModalityRepository repository;

    public DeleteEncounterModalityUseCase(EncounterModalityRepository repository) {
        this.repository = repository;
    }

    public void execute(EncounterModalityId id) {

        var modality = repository.findById(id)
                .orElseThrow(() -> new EncounterModalityNotFoundApplicationException(id));

        modality.delete();
        repository.delete(modality);
    }
}