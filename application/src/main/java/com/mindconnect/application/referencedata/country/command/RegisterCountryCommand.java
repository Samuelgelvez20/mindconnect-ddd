package com.mindconnect.application.referencedata.country.command;

import java.util.Objects;

public record RegisterCountryCommand(
        String name,
        String code,
        String description,
        String telephonePrefix
) {

    public RegisterCountryCommand {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(code, "code must not be null");
    }
}
