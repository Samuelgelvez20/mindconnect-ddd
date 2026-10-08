package com.mindconnect.domain.treatment.treatmentstatus.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;

public interface TreatmentStatusRepository {

    TreatmentStatus save(TreatmentStatus treatmentStatus);

    Optional<TreatmentStatus> findById(TreatmentStatusId id);

    List<TreatmentStatus> findAll();

    void delete(TreatmentStatus treatmentStatus);
}