package com.mindconnect.application.clinicalrecord.encountermodality.usecase;

import java.util.List;

import com.mindconnect.application.clinicalrecord.encountermodality.dto.EncounterModalityResponse;
import com.mindconnect.domain.clinicalrecord.encountermodality.port.repository.EncounterModalityRepository;

public class ListEncounterModalityUseCase {

    private final EncounterModalityRepository repository;

    public ListEncounterModalityUseCase(EncounterModalityRepository repository) {
        this.repository = repository;
    }

    public List<EncounterModalityResponse> execute() {
        return repository.findAll()
                .stream()
                .map(EncounterModalityResponse::from)
                .toList();
    }
}