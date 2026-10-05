package com.mindconnect.application.treatment.treatmentgoalstatus.usecase;

import com.mindconnect.application.treatment.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class DeleteTreatmentGoalStatusUseCase {

    private final TreatmentGoalStatusRepository repository;

    public DeleteTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        this.repository = repository;
    }

    public void execute(TreatmentGoalStatusId id) {
        TreatmentGoalStatus status = repository.findById(id)
                .orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException("TreatmentGoalStatus with id " + id.value() + " not found"));

        repository.delete(status);
    }
}