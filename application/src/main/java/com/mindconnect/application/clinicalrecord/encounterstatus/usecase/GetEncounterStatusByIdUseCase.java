package com.mindconnect.application.clinicalrecord.encounterstatus.usecase;

import com.mindconnect.application.clinicalrecord.encounterstatus.dto.EncounterStatusResponse;
import com.mindconnect.application.clinicalrecord.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository.EncounterStatusRepository;

public class GetEncounterStatusByIdUseCase {

    private final EncounterStatusRepository repository;

    public GetEncounterStatusByIdUseCase(EncounterStatusRepository repository) {
        this.repository = repository;
    }

    public EncounterStatusResponse execute(EncounterStatusId id) {
        return repository.findById(id)
                .map(EncounterStatusResponse::from)
                .orElseThrow(() -> new EncounterStatusNotFoundApplicationException(id));
    }
}