package com.mindconnect.application.clinicalrecord.encounterstatus.usecase;

import com.mindconnect.application.clinicalrecord.encounterstatus.command.UpdateEncounterStatusCommand;
import com.mindconnect.application.clinicalrecord.encounterstatus.dto.EncounterStatusResponse;
import com.mindconnect.application.clinicalrecord.encounterstatus.exception.EncounterStatusAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalrecord.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.aggregate.EncounterStatus;
import com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository.EncounterStatusRepository;

public class UpdateEncounterStatusUseCase {

    private final EncounterStatusRepository repository;

    public UpdateEncounterStatusUseCase(EncounterStatusRepository repository) {
        this.repository = repository;
    }

    public EncounterStatusResponse execute(UpdateEncounterStatusCommand command) {

        var status = repository.findById(command.id())
                .orElseThrow(() -> new EncounterStatusNotFoundApplicationException(command.id()));

        status.update(command.code(), command.name(), command.active());

        if (repository.existsByCodeAndIdNot(status.code(), status.id())) {
            throw new EncounterStatusAlreadyExistsApplicationException(status.code());
        }
        if (repository.existsByNameAndIdNot(status.name(), status.id())) {
            throw new EncounterStatusAlreadyExistsApplicationException(status.code(), status.name());
        }

        return EncounterStatusResponse.from(repository.save(status));
    }
}