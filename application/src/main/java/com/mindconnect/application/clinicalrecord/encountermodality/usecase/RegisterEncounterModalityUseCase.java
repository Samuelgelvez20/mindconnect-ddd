package com.mindconnect.application.clinicalrecord.encountermodality.usecase;

import com.mindconnect.application.clinicalrecord.encountermodality.command.RegisterEncounterModalityCommand;
import com.mindconnect.application.clinicalrecord.encountermodality.dto.EncounterModalityResponse;
import com.mindconnect.application.clinicalrecord.encountermodality.exception.EncounterModalityAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.aggregate.EncounterModality;
import com.mindconnect.domain.clinicalrecord.encountermodality.port.repository.EncounterModalityRepository;

public class RegisterEncounterModalityUseCase {

    private final EncounterModalityRepository repository;

    public RegisterEncounterModalityUseCase(EncounterModalityRepository repository) {
        this.repository = repository;
    }

    public EncounterModalityResponse execute(RegisterEncounterModalityCommand command) {

        EncounterModality modality = EncounterModality.register(command.code(), command.name());

        if (repository.existsByCode(modality.code())) {
            throw new EncounterModalityAlreadyExistsApplicationException(modality.code());
        }

        return EncounterModalityResponse.from(repository.save(modality));
    }
}