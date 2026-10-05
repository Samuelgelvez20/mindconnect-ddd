package com.mindconnect.application.patient.patient.usecase;

import com.mindconnect.application.patient.patient.dto.PatientResponse;
import com.mindconnect.application.patient.patient.exception.PatientNotFoundApplicationException;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;

public class GetPatientByIdUseCase {

    private final PatientRepository patientRepository;

    public GetPatientByIdUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse execute(PatientId id) {
        return patientRepository.findById(id)
                .map(PatientResponse::from)
                .orElseThrow(() -> new PatientNotFoundApplicationException(id));
    }
}