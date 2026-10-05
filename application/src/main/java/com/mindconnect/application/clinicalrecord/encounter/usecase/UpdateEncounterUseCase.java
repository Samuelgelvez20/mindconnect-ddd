package com.mindconnect.application.clinicalrecord.encounter.usecase;

import com.mindconnect.application.clinicalrecord.encounter.command.UpdateEncounterCommand;
import com.mindconnect.application.clinicalrecord.encounter.dto.EncounterResponse;
import com.mindconnect.application.clinicalrecord.encounter.exception.EncounterNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounter.model.aggregate.Encounter;
import com.mindconnect.domain.clinicalrecord.encounter.port.repository.EncounterRepository;

public class UpdateEncounterUseCase {

    private final EncounterRepository repository;

    public UpdateEncounterUseCase(EncounterRepository repository) {
        this.repository = repository;
    }

    public EncounterResponse execute(UpdateEncounterCommand command) {

        var encounter = repository.findById(command.id())
                .orElseThrow(() -> new EncounterNotFoundApplicationException(command.id()));

        encounter.update(
                command.clinicalRecordId(),
                command.professionalId(),
                command.encounterTypeId(),
                command.startedAt(),
                command.endedAt(),
                command.reasonForVisit(),
                command.currentCondition(),
                command.modalityId(),
                command.statusId(),
                command.updatedBy());

        return EncounterResponse.from(repository.save(encounter));
    }
}