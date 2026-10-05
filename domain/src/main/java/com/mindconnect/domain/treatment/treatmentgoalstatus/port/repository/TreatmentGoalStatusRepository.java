package com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;

public interface TreatmentGoalStatusRepository {

    TreatmentGoalStatus save(TreatmentGoalStatus treatmentGoalStatus);

    Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id);

    List<TreatmentGoalStatus> findAll();

    void delete(TreatmentGoalStatus treatmentGoalStatus);
}