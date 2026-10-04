package com.mindconnect.application.referencedata.stateregion.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;

public record StateRegionResponse(
        UUID id,
        String name,
        String code,
        String description,
        UUID countryId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static StateRegionResponse from(StateRegion stateRegion) {
        return new StateRegionResponse(
                stateRegion.id().value(),
                stateRegion.name(),
                stateRegion.code(),
                stateRegion.description(),
                stateRegion.countryId().value(),
                stateRegion.active(),
                stateRegion.createdAt(),
                stateRegion.updatedAt()
        );
    }
}