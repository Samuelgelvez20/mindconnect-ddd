package com.mindconnect.application.clinicalrecord.encountertype.usecase;

import com.mindconnect.application.clinicalrecord.encountertype.command.UpdateEncounterTypeCommand;
import com.mindconnect.application.clinicalrecord.encountertype.dto.EncounterTypeResponse;
import com.mindconnect.application.clinicalrecord.encountertype.exception.EncounterTypeAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalrecord.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountertype.model.aggregate.EncounterType;
import com.mindconnect.domain.clinicalrecord.encountertype.port.repository.EncounterTypeRepository;

public class UpdateEncounterTypeUseCase {

    private final EncounterTypeRepository repository;

    public UpdateEncounterTypeUseCase(EncounterTypeRepository repository) {
        this.repository = repository;
    }

    public EncounterTypeResponse execute(UpdateEncounterTypeCommand command) {

        var type = repository.findById(command.id())
                .orElseThrow(() -> new EncounterTypeNotFoundApplicationException(command.id()));

        type.update(command.code(), command.name(), command.active());

        if (repository.existsByCodeAndIdNot(type.code(), type.id())) {
            throw new EncounterTypeAlreadyExistsApplicationException(type.code());
        }

        return EncounterTypeResponse.from(repository.save(type));
    }
}