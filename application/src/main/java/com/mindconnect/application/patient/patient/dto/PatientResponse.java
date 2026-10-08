package com.mindconnect.application.patient.patient.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public record PatientResponse(
        UUID id,
        UUID documentTypeId,
        String documentNumber,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        LocalDate birthDate,
        UUID biologicalSexId,
        UUID genderIdentityId,
        String email,
        String phone,
        String address,
        boolean active,
        UUID createdBy,
        UUID updatedBy,
        UUID cityId,
        Instant createdAt,
        Instant updatedAt
) {

    public static PatientResponse from(Patient patient) {
        return new PatientResponse(
                patient.id().value(),
                patient.documentTypeId().value(),
                patient.documentNumber(),
                patient.firstName(),
                patient.middleName(),
                patient.lastName(),
                patient.secondLastName(),
                patient.birthDate(),
                patient.biologicalSexId().value(),
                patient.genderIdentityId().value(),
                patient.email(),
                patient.phone(),
                patient.address(),
                patient.active(),
                patient.createdBy() != null ? patient.createdBy().value() : null,
                patient.updatedBy() != null ? patient.updatedBy().value() : null,
                patient.cityId().value(),
                patient.createdAt(),
                patient.updatedAt()
        );
    }
}