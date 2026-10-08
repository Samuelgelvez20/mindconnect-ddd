package com.mindconnect.application.treatment.treatmentgoal.usecase;

import com.mindconnect.application.treatment.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.domain.treatment.treatmentgoal.port.repository.TreatmentGoalRepository;

public class DeleteTreatmentGoalUseCase {

    private final TreatmentGoalRepository repository;

    public DeleteTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        this.repository = repository;
    }

    public void execute(TreatmentGoalId id) {
        TreatmentGoal goal = repository.findById(id)
                .orElseThrow(() -> new TreatmentGoalNotFoundApplicationException("TreatmentGoal with id " + id.value() + " not found"));

        repository.delete(goal);
    }
}