package com.mindconnect.domain.professional.professional.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public record ProfessionalRegisteredEvent(
        ProfessionalId id,
        Instant occurredOn
) implements DomainEvent {

    public ProfessionalRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}