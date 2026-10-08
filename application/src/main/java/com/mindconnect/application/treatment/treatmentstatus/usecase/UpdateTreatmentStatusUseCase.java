package com.mindconnect.application.treatment.treatmentstatus.usecase;

import com.mindconnect.application.treatment.treatmentstatus.dto.TreatmentStatusResponse;
import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.application.treatment.treatmentstatus.command.UpdateTreatmentStatusCommand;
import com.mindconnect.application.treatment.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentstatus.port.repository.TreatmentStatusRepository;

public class UpdateTreatmentStatusUseCase {

    private final TreatmentStatusRepository repository;

    public UpdateTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        this.repository = repository;
    }

    public TreatmentStatusResponse execute(UpdateTreatmentStatusCommand command) {
        TreatmentStatus status = repository.findById(TreatmentStatusId.generate())
                .orElseThrow(() -> new TreatmentStatusNotFoundApplicationException("TreatmentStatus with code " + command.code() + " not found"));

        TreatmentStatus updated = TreatmentStatus.register(command.code(), command.name());

        repository.save(updated);

        return TreatmentStatusResponse.from(updated);
    }
}