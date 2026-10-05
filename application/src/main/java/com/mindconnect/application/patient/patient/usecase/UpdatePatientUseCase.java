package com.mindconnect.application.patient.patient.usecase;

import com.mindconnect.application.patient.patient.command.UpdatePatientCommand;
import com.mindconnect.application.patient.patient.dto.PatientResponse;
import com.mindconnect.application.patient.patient.exception.PatientAlreadyExistsApplicationException;
import com.mindconnect.application.patient.patient.exception.PatientNotFoundApplicationException;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class UpdatePatientUseCase {

    private final PatientRepository patientRepository;

    public UpdatePatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse execute(UpdatePatientCommand command) {

        var patient = patientRepository.findById(command.id())
                .orElseThrow(() -> new PatientNotFoundApplicationException(command.id()));

        patient.update(
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
                command.active(),
                command.updatedBy(),
                command.cityId());

        if (patientRepository.existsByEmailAndIdNot(patient.email(), patient.id())) {
            throw new PatientAlreadyExistsApplicationException(patient.email());
        }

        if (patientRepository.existsByDocumentTypeIdAndDocumentNumberAndIdNot(
                patient.documentTypeId(), patient.documentNumber(), patient.id())) {
            throw new PatientAlreadyExistsApplicationException(
                    patient.documentTypeId(), patient.documentNumber());
        }

        return PatientResponse.from(patientRepository.save(patient));
    }
}