package com.mindconnect.application.treatment.treatmentgoalstatus.usecase;

import com.mindconnect.application.treatment.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.application.treatment.treatmentgoalstatus.command.UpdateTreatmentGoalStatusCommand;
import com.mindconnect.application.treatment.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class UpdateTreatmentGoalStatusUseCase {

    private final TreatmentGoalStatusRepository repository;

    public UpdateTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalStatusResponse execute(UpdateTreatmentGoalStatusCommand command) {
        TreatmentGoalStatus status = repository.findById(TreatmentGoalStatusId.generate())
                .orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException("TreatmentGoalStatus with code " + command.code() + " not found"));

        TreatmentGoalStatus updated = TreatmentGoalStatus.register(command.code(), command.name());

        repository.save(updated);

        return TreatmentGoalStatusResponse.from(updated);
    }
}