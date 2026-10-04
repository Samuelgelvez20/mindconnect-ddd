package com.mindconnect.application.referencedata.stateregion.command;

import java.util.Objects;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public record RegisterStateRegionCommand(
        String name,
        String code,
        String description,
        CountryId countryId
) {

    public RegisterStateRegionCommand {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(countryId, "countryId must not be null");
    }
}