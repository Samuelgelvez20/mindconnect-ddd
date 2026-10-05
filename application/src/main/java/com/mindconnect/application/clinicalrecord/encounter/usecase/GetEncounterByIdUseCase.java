package com.mindconnect.application.clinicalrecord.encounter.usecase;

import com.mindconnect.application.clinicalrecord.encounter.dto.EncounterResponse;
import com.mindconnect.application.clinicalrecord.encounter.exception.EncounterNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.encounter.port.repository.EncounterRepository;

public class GetEncounterByIdUseCase {

    private final EncounterRepository repository;

    public GetEncounterByIdUseCase(EncounterRepository repository) {
        this.repository = repository;
    }

    public EncounterResponse execute(EncounterId id) {
        return repository.findById(id)
                .map(EncounterResponse::from)
                .orElseThrow(() -> new EncounterNotFoundApplicationException(id));
    }
}