package com.mindconnect.application.clinicalrecord.encounter.usecase;

import java.util.List;

import com.mindconnect.application.clinicalrecord.encounter.dto.EncounterResponse;
import com.mindconnect.domain.clinicalrecord.encounter.port.repository.EncounterRepository;

public class ListEncounterUseCase {

    private final EncounterRepository repository;

    public ListEncounterUseCase(EncounterRepository repository) {
        this.repository = repository;
    }

    public List<EncounterResponse> execute() {
        return repository.findAll()
                .stream()
                .map(EncounterResponse::from)
                .toList();
    }
}