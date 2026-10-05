package com.mindconnect.application.clinicalrecord.encountermodality.usecase;

import com.mindconnect.application.clinicalrecord.encountermodality.dto.EncounterModalityResponse;
import com.mindconnect.application.clinicalrecord.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encountermodality.port.repository.EncounterModalityRepository;

public class GetEncounterModalityByIdUseCase {

    private final EncounterModalityRepository repository;

    public GetEncounterModalityByIdUseCase(EncounterModalityRepository repository) {
        this.repository = repository;
    }

    public EncounterModalityResponse execute(EncounterModalityId id) {
        return repository.findById(id)
                .map(EncounterModalityResponse::from)
                .orElseThrow(() -> new EncounterModalityNotFoundApplicationException(id));
    }
}