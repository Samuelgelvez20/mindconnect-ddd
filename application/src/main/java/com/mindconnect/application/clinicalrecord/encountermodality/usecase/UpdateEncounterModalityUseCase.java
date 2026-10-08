package com.mindconnect.application.clinicalrecord.encountermodality.usecase;

import com.mindconnect.application.clinicalrecord.encountermodality.command.UpdateEncounterModalityCommand;
import com.mindconnect.application.clinicalrecord.encountermodality.dto.EncounterModalityResponse;
import com.mindconnect.application.clinicalrecord.encountermodality.exception.EncounterModalityAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalrecord.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.aggregate.EncounterModality;
import com.mindconnect.domain.clinicalrecord.encountermodality.port.repository.EncounterModalityRepository;

public class UpdateEncounterModalityUseCase {

    private final EncounterModalityRepository repository;

    public UpdateEncounterModalityUseCase(EncounterModalityRepository repository) {
        this.repository = repository;
    }

    public EncounterModalityResponse execute(UpdateEncounterModalityCommand command) {

        var modality = repository.findById(command.id())
                .orElseThrow(() -> new EncounterModalityNotFoundApplicationException(command.id()));

        modality.update(command.code(), command.name(), command.active());

        if (repository.existsByCodeAndIdNot(modality.code(), modality.id())) {
            throw new EncounterModalityAlreadyExistsApplicationException(modality.code());
        }

        return EncounterModalityResponse.from(repository.save(modality));
    }
}