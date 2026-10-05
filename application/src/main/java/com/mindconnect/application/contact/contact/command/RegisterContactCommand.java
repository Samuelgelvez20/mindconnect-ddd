package com.mindconnect.application.contact.contact.command;

import java.util.Objects;

import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

public record RegisterContactCommand(
        String fullName,
        String email,
        String notes,
        CityMunicipalityId cityId,
        ProfessionalId createdBy) {

    public RegisterContactCommand {
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(cityId, "cityId must not be null");
        Objects.requireNonNull(createdBy, "createdBy must not be null");
    }
}