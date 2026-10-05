package com.mindconnect.application.treatment.treatmentgoalstatus.usecase;

import com.mindconnect.application.treatment.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.application.treatment.treatmentgoalstatus.command.RegisterTreatmentGoalStatusCommand;
import com.mindconnect.application.treatment.treatmentgoalstatus.exception.TreatmentGoalStatusAlreadyExistsApplicationException;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class RegisterTreatmentGoalStatusUseCase {

    private final TreatmentGoalStatusRepository repository;

    public RegisterTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalStatusResponse execute(RegisterTreatmentGoalStatusCommand command) {
        TreatmentGoalStatus existing = repository.findById(TreatmentGoalStatusId.generate())
                .filter(t -> t.code().equals(command.code()))
                .orElse(null);

        if (existing != null) {
            throw new TreatmentGoalStatusAlreadyExistsApplicationException("TreatmentGoalStatus with code " + command.code() + " already exists");
        }

        TreatmentGoalStatus status = TreatmentGoalStatus.register(command.code(), command.name());
        repository.save(status);

        return TreatmentGoalStatusResponse.from(status);
    }
}