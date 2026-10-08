package com.mindconnect.application.patient.patient.command;

import java.time.LocalDate;
import java.util.Objects;

import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public record UpdatePatientCommand(
        PatientId id,
        DocumentTypeId documentTypeId,
        String documentNumber,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        java.time.LocalDate birthDate,
        GenderId biologicalSexId,
        GenderId genderIdentityId,
        String email,
        String phone,
        String address,
        boolean active,
        ProfessionalId updatedBy,
        CityMunicipalityId cityId) {

    public UpdatePatientCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(documentTypeId, "documentTypeId must not be null");
        Objects.requireNonNull(birthDate, "birthDate must not be null");
        Objects.requireNonNull(updatedBy, "updatedBy must not be null");
    }
}