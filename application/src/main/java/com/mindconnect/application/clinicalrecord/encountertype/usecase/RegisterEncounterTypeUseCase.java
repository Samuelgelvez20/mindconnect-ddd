package com.mindconnect.application.clinicalrecord.encountertype.usecase;

import com.mindconnect.application.clinicalrecord.encountertype.command.RegisterEncounterTypeCommand;
import com.mindconnect.application.clinicalrecord.encountertype.dto.EncounterTypeResponse;
import com.mindconnect.application.clinicalrecord.encountertype.exception.EncounterTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalrecord.encountertype.model.aggregate.EncounterType;
import com.mindconnect.domain.clinicalrecord.encountertype.port.repository.EncounterTypeRepository;

public class RegisterEncounterTypeUseCase {

    private final EncounterTypeRepository repository;

    public RegisterEncounterTypeUseCase(EncounterTypeRepository repository) {
        this.repository = repository;
    }

    public EncounterTypeResponse execute(RegisterEncounterTypeCommand command) {

        EncounterType type = EncounterType.register(command.code(), command.name());

        if (repository.existsByCode(type.code())) {
            throw new EncounterTypeAlreadyExistsApplicationException(type.code());
        }

        return EncounterTypeResponse.from(repository.save(type));
    }
}