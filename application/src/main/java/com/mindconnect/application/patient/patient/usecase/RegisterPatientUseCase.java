package com.mindconnect.application.patient.patient.usecase;

import com.mindconnect.application.patient.patient.command.RegisterPatientCommand;
import com.mindconnect.application.patient.patient.dto.PatientResponse;
import com.mindconnect.application.patient.patient.exception.PatientAlreadyExistsApplicationException;
import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class RegisterPatientUseCase {

    private final PatientRepository patientRepository;

    public RegisterPatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse execute(RegisterPatientCommand command) {

        Patient patient = Patient.register(
                command.documentTypeId(),
                command.documentNumber(),
                command.firstName(),
                command.middleName(),
                command.lastName(),
                command.secondLastName(),
                command.birthDate(),
                command.biologicalSexId(),
                command.genderIdentityId(),
                command.email(),
                command.phone(),
                command.address(),
                command.createdBy(),
                command.cityId());

        if (patientRepository.existsByEmail(patient.email())) {
            throw new PatientAlreadyExistsApplicationException(patient.email());
        }

        if (patientRepository.existsByDocumentTypeIdAndDocumentNumber(
                patient.documentTypeId(), patient.documentNumber())) {
            throw new PatientAlreadyExistsApplicationException(
                    patient.documentTypeId(), patient.documentNumber());
        }

        return PatientResponse.from(patientRepository.save(patient));
    }
}