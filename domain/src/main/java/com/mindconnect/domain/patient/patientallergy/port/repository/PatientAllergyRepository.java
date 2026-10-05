package com.mindconnect.domain.patient.patientallergy.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;

public interface PatientAllergyRepository {

    PatientAllergy save(PatientAllergy patientAllergy);

    Optional<PatientAllergy> findById(PatientAllergyId id);

    List<PatientAllergy> findAll();

    void delete(PatientAllergy patientAllergy);
}