package com.mindconnect.application.referencedata.citymunicipality.command;

import java.util.Objects;

import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public record RegisterCityMunicipalityCommand(
        String name,
        String code,
        String description,
        StateRegionId regionId
) {

    public RegisterCityMunicipalityCommand {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(regionId, "regionId must not be null");
    }
}