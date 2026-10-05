package com.mindconnect.application.patient.patientallergy.usecase;

import com.mindconnect.application.patient.patientallergy.dto.PatientAllergyResponse;
import com.mindconnect.application.patient.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;

public class GetPatientAllergyByIdUseCase {

    private final PatientAllergyRepository patientAllergyRepository;

    public GetPatientAllergyByIdUseCase(PatientAllergyRepository patientAllergyRepository) {
        this.patientAllergyRepository = patientAllergyRepository;
    }

    public PatientAllergyResponse execute(PatientAllergyId id) {
        return patientAllergyRepository.findById(id)
                .map(PatientAllergyResponse::from)
                .orElseThrow(() -> new PatientAllergyNotFoundApplicationException(id));
    }
}