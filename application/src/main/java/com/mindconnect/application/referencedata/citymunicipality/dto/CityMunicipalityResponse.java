package com.mindconnect.application.referencedata.citymunicipality.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public record CityMunicipalityResponse(
        UUID id,
        String name,
        String code,
        String description,
        UUID regionId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static CityMunicipalityResponse from(CityMunicipality cityMunicipality) {
        return new CityMunicipalityResponse(
                cityMunicipality.id().value(),
                cityMunicipality.name(),
                cityMunicipality.code(),
                cityMunicipality.description(),
                cityMunicipality.regionId().value(),
                cityMunicipality.active(),
                cityMunicipality.createdAt(),
                cityMunicipality.updatedAt()
        );
    }
}