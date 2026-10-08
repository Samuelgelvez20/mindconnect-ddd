package com.mindconnect.application.treatment.treatmentgoal.usecase;

import com.mindconnect.application.treatment.treatmentgoal.dto.TreatmentGoalResponse;
import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.application.treatment.treatmentgoal.command.RegisterTreatmentGoalCommand;
import com.mindconnect.application.treatment.treatmentgoal.exception.TreatmentGoalAlreadyExistsApplicationException;
import com.mindconnect.domain.treatment.treatmentgoal.port.repository.TreatmentGoalRepository;

public class RegisterTreatmentGoalUseCase {

    private final TreatmentGoalRepository repository;

    public RegisterTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalResponse execute(RegisterTreatmentGoalCommand command) {

        TreatmentGoalId id = TreatmentGoalId.generate();

        TreatmentGoal existing = repository.findById(id)
                .filter(t -> t.description().equals(command.description()))
                .orElse(null);

        if (existing != null) {
            throw new TreatmentGoalAlreadyExistsApplicationException("TreatmentGoal with description " + command.description() + " already exists");
        }

        TreatmentPlanId treatmentPlanId = new TreatmentPlanId(java.util.UUID.fromString(command.treatmentPlanId()));
        TreatmentGoalStatusId treatmentGoalStatusId = new TreatmentGoalStatusId(java.util.UUID.fromString(command.treatmentGoalStatusId()));

        TreatmentGoal goal = TreatmentGoal.register(treatmentPlanId, command.description(), java.time.LocalDate.parse(command.targetDate()), treatmentGoalStatusId);
        repository.save(goal);

        return TreatmentGoalResponse.from(goal);
    }
}