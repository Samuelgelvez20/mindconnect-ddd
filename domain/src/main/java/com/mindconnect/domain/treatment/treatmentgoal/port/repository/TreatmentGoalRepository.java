package com.mindconnect.domain.treatment.treatmentgoal.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;

public interface TreatmentGoalRepository {

    TreatmentGoal save(TreatmentGoal treatmentGoal);

    Optional<TreatmentGoal> findById(TreatmentGoalId id);

    List<TreatmentGoal> findAll();

    void delete(TreatmentGoal treatmentGoal);
}