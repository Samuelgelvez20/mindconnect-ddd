package com.mindconnect.application.clinicalrecord.encounter.usecase;

import com.mindconnect.application.clinicalrecord.encounter.command.RegisterEncounterCommand;
import com.mindconnect.application.clinicalrecord.encounter.dto.EncounterResponse;
import com.mindconnect.application.clinicalrecord.encounter.exception.EncounterNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounter.model.aggregate.Encounter;
import com.mindconnect.domain.clinicalrecord.encounter.port.repository.EncounterRepository;

public class RegisterEncounterUseCase {

    private final EncounterRepository repository;

    public RegisterEncounterUseCase(EncounterRepository repository) {
        this.repository = repository;
    }

    public EncounterResponse execute(RegisterEncounterCommand command) {

        Encounter encounter = Encounter.register(
                command.clinicalRecordId(),
                command.professionalId(),
                command.encounterTypeId(),
                command.startedAt(),
                command.modalityId(),
                command.statusId(),
                command.createdBy());

        return EncounterResponse.from(repository.save(encounter));
    }
}