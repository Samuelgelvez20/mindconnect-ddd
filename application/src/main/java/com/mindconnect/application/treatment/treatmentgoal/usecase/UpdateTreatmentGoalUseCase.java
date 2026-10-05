package com.mindconnect.application.treatment.treatmentgoal.usecase;

import com.mindconnect.application.treatment.treatmentgoal.dto.TreatmentGoalResponse;
import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.application.treatment.treatmentgoal.command.UpdateTreatmentGoalCommand;
import com.mindconnect.application.treatment.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentgoal.port.repository.TreatmentGoalRepository;

public class UpdateTreatmentGoalUseCase {

    private final TreatmentGoalRepository repository;

    public UpdateTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalResponse execute(UpdateTreatmentGoalCommand command) {

        TreatmentGoalId id = new TreatmentGoalId(java.util.UUID.fromString(command.treatmentGoalId()));

        TreatmentGoal goal = repository.findById(id)
                .orElseThrow(() -> new TreatmentGoalNotFoundApplicationException("TreatmentGoal with id " + id.value() + " not found"));

        TreatmentPlanId treatmentPlanId = new TreatmentPlanId(java.util.UUID.fromString(command.treatmentPlanId()));
        TreatmentGoalStatusId treatmentGoalStatusId = new TreatmentGoalStatusId(java.util.UUID.fromString(command.treatmentGoalStatusId()));

        goal.update(treatmentPlanId, command.description(), java.time.LocalDate.parse(command.targetDate()), treatmentGoalStatusId);

        repository.save(goal);

        return TreatmentGoalResponse.from(goal);
    }
}