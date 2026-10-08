package com.mindconnect.application.patient.patient.command;

import java.time.LocalDate;
import java.util.Objects;

import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public record RegisterPatientCommand(
        DocumentTypeId documentTypeId,
        String documentNumber,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        LocalDate birthDate,
        GenderId biologicalSexId,
        GenderId genderIdentityId,
        String email,
        String phone,
        String address,
        ProfessionalId createdBy,
        CityMunicipalityId cityId) {

    public RegisterPatientCommand {
        Objects.requireNonNull(documentTypeId, "documentTypeId must not be null");
        Objects.requireNonNull(documentNumber, "documentNumber must not be null");
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
        Objects.requireNonNull(birthDate, "birthDate must not be null");
        Objects.requireNonNull(cityId, "cityId must not be null");
    }
}