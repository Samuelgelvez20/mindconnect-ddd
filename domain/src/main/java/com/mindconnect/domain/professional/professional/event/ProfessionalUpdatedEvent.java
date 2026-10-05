package com.mindconnect.domain.professional.professional.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public record ProfessionalUpdatedEvent(
        ProfessionalId id,
        DocumentTypeId documentTypeId,
        String documentNumber,
        String firstName,
        String lastName,
        ProfessionalTypeId professionalTypeId,
        String licenseNumber,
        boolean active,
        CityMunicipalityId cityId,
        Instant occurredOn
) implements DomainEvent {

    public ProfessionalUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(documentTypeId, "documentTypeId must not be null");
        Objects.requireNonNull(documentNumber, "documentNumber must not be null");
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
        Objects.requireNonNull(professionalTypeId, "professionalTypeId must not be null");
        Objects.requireNonNull(licenseNumber, "licenseNumber must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}