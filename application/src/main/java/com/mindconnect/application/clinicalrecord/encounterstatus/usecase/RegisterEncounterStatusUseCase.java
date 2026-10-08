package com.mindconnect.application.clinicalrecord.encounterstatus.usecase;

import com.mindconnect.application.clinicalrecord.encounterstatus.command.RegisterEncounterStatusCommand;
import com.mindconnect.application.clinicalrecord.encounterstatus.dto.EncounterStatusResponse;
import com.mindconnect.application.clinicalrecord.encounterstatus.exception.EncounterStatusAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.aggregate.EncounterStatus;
import com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository.EncounterStatusRepository;

public class RegisterEncounterStatusUseCase {

    private final EncounterStatusRepository repository;

    public RegisterEncounterStatusUseCase(EncounterStatusRepository repository) {
        this.repository = repository;
    }

    public EncounterStatusResponse execute(RegisterEncounterStatusCommand command) {

        EncounterStatus status = EncounterStatus.register(command.code(), command.name());

        if (repository.existsByCode(status.code())) {
            throw new EncounterStatusAlreadyExistsApplicationException(status.code());
        }
        if (repository.existsByName(status.name())) {
            throw new EncounterStatusAlreadyExistsApplicationException(status.code(), status.name());
        }

        return EncounterStatusResponse.from(repository.save(status));
    }
}