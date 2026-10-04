package com.mindconnect.domain.referencedata.citymunicipality.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

public record CityMunicipalityDeletedEvent(CityMunicipalityId id, Instant occurredOn) implements DomainEvent {

    public CityMunicipalityDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}