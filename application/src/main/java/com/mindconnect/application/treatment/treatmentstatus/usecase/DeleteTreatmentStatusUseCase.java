package com.mindconnect.application.treatment.treatmentstatus.usecase;

import com.mindconnect.application.treatment.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.domain.treatment.treatmentstatus.port.repository.TreatmentStatusRepository;

public class DeleteTreatmentStatusUseCase {

    private final TreatmentStatusRepository repository;

    public DeleteTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        this.repository = repository;
    }

    public void execute(TreatmentStatusId id) {
        TreatmentStatus status = repository.findById(id)
                .orElseThrow(() -> new TreatmentStatusNotFoundApplicationException("TreatmentStatus with id " + id.value() + " not found"));

        repository.delete(status);
    }
}