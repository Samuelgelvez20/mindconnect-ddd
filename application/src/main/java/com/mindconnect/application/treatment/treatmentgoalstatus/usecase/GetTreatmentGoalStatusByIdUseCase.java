package com.mindconnect.application.treatment.treatmentgoalstatus.usecase;

import com.mindconnect.application.treatment.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.application.treatment.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class GetTreatmentGoalStatusByIdUseCase {

    private final TreatmentGoalStatusRepository repository;

    public GetTreatmentGoalStatusByIdUseCase(TreatmentGoalStatusRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalStatusResponse execute(TreatmentGoalStatusId id) {
        TreatmentGoalStatus status = repository.findById(id)
                .orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException("TreatmentGoalStatus with id " + id.value() + " not found"));

        return TreatmentGoalStatusResponse.from(status);
    }
}