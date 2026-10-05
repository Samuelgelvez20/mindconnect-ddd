package com.mindconnect.application.contact.contact.command;

import java.util.Objects;

import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

public record UpdateContactCommand(
        ContactId id,
        String fullName,
        String email,
        String notes,
        CityMunicipalityId cityId,
        ProfessionalId updatedBy) {

    public UpdateContactCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(cityId, "cityId must not be null");
        Objects.requireNonNull(updatedBy, "updatedBy must not be null");
    }
}