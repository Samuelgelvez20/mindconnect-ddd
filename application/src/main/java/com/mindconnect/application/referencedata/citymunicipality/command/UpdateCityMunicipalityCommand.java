package com.mindconnect.application.referencedata.citymunicipality.command;

import java.util.Objects;

import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

public record UpdateCityMunicipalityCommand(
        CityMunicipalityId id,
        String name,
        String code,
        String description,
        boolean active
) {

    public UpdateCityMunicipalityCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(code, "code must not be null");
    }
}