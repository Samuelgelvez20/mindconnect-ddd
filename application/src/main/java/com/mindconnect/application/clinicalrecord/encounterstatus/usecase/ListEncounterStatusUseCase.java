package com.mindconnect.application.clinicalrecord.encounterstatus.usecase;

import java.util.List;

import com.mindconnect.application.clinicalrecord.encounterstatus.dto.EncounterStatusResponse;
import com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository.EncounterStatusRepository;

public class ListEncounterStatusUseCase {

    private final EncounterStatusRepository repository;

    public ListEncounterStatusUseCase(EncounterStatusRepository repository) {
        this.repository = repository;
    }

    public List<EncounterStatusResponse> execute() {
        return repository.findAll()
                .stream()
                .map(EncounterStatusResponse::from)
                .toList();
    }
}