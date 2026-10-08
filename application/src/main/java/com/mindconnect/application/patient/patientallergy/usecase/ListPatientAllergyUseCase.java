package com.mindconnect.application.patient.patientallergy.usecase;

import java.util.List;

import com.mindconnect.application.patient.patientallergy.dto.PatientAllergyResponse;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;

public class ListPatientAllergyUseCase {

    private final PatientAllergyRepository patientAllergyRepository;

    public ListPatientAllergyUseCase(PatientAllergyRepository patientAllergyRepository) {
        this.patientAllergyRepository = patientAllergyRepository;
    }

    public List<PatientAllergyResponse> execute() {
        return patientAllergyRepository.findAll()
                .stream()
                .map(PatientAllergyResponse::from)
                .toList();
    }
}