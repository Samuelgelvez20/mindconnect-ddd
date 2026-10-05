package com.mindconnect.application.clinicalrecord.encounterstatus.usecase;

import com.mindconnect.application.clinicalrecord.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository.EncounterStatusRepository;

public class DeleteEncounterStatusUseCase {

    private final EncounterStatusRepository repository;

    public DeleteEncounterStatusUseCase(EncounterStatusRepository repository) {
        this.repository = repository;
    }

    public void execute(EncounterStatusId id) {

        var status = repository.findById(id)
                .orElseThrow(() -> new EncounterStatusNotFoundApplicationException(id));

        status.delete();
        repository.delete(status);
    }
}