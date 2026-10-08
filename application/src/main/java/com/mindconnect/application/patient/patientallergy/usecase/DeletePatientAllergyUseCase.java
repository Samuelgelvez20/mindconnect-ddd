package com.mindconnect.application.patient.patientallergy.usecase;

import com.mindconnect.application.patient.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;

public class DeletePatientAllergyUseCase {

    private final PatientAllergyRepository patientAllergyRepository;

    public DeletePatientAllergyUseCase(PatientAllergyRepository patientAllergyRepository) {
        this.patientAllergyRepository = patientAllergyRepository;
    }

    public void execute(PatientAllergyId id) {

        var patientAllergy = patientAllergyRepository.findById(id)
                .orElseThrow(() -> new PatientAllergyNotFoundApplicationException(id));

        patientAllergy.delete();
        patientAllergyRepository.delete(patientAllergy);
    }
}