package com.mindconnect.domain.referencedata.citymunicipality.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

public record CityMunicipalityUpdatedEvent(
        CityMunicipalityId id,
        String name,
        String code,
        Instant occurredOn
) implements DomainEvent {

    public CityMunicipalityUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}