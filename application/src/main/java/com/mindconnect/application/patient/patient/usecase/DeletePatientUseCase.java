package com.mindconnect.application.patient.patient.usecase;

import com.mindconnect.application.patient.patient.exception.PatientNotFoundApplicationException;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;

public class DeletePatientUseCase {

    private final PatientRepository patientRepository;

    public DeletePatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void execute(PatientId id) {

        var patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundApplicationException(id));

        patient.delete();
        patientRepository.delete(patient);
    }
}