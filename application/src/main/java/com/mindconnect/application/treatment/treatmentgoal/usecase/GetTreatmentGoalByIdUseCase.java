package com.mindconnect.application.treatment.treatmentgoal.usecase;

import com.mindconnect.application.treatment.treatmentgoal.dto.TreatmentGoalResponse;
import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.application.treatment.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentgoal.port.repository.TreatmentGoalRepository;

public class GetTreatmentGoalByIdUseCase {

    private final TreatmentGoalRepository repository;

    public GetTreatmentGoalByIdUseCase(TreatmentGoalRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalResponse execute(TreatmentGoalId id) {
        TreatmentGoal goal = repository.findById(id)
                .orElseThrow(() -> new TreatmentGoalNotFoundApplicationException("TreatmentGoal with id " + id.value() + " not found"));

        return TreatmentGoalResponse.from(goal);
    }
}