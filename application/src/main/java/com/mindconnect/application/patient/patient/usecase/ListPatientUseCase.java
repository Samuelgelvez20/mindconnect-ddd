package com.mindconnect.application.patient.patient.usecase;

import java.util.List;

import com.mindconnect.application.patient.patient.dto.PatientResponse;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;

public class ListPatientUseCase {

    private final PatientRepository patientRepository;

    public ListPatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponse> execute() {
        return patientRepository.findAll()
                .stream()
                .map(PatientResponse::from)
                .toList();
    }
}