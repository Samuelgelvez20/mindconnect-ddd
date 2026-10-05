package com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate.ClinicalRecord;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;

public interface ClinicalRecordRepository {

    ClinicalRecord save(ClinicalRecord clinicalRecord);

    Optional<ClinicalRecord> findById(ClinicalRecordId id);

    List<ClinicalRecord> findAll();

    List<ClinicalRecord> findByPatientId(PatientId patientId);

    void delete(ClinicalRecord clinicalRecord);
}