package com.mindconnect.application.referencedata.country.command;

import java.util.Objects;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public record UpdateCountryCommand(
        CountryId id,
        String name,
        String code,
        String description,
        String telephonePrefix,
        boolean active
) {

    public UpdateCountryCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(code, "code must not be null");
    }
}
