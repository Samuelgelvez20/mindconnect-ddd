package com.mindconnect.application.referencedata.stateregion.command;

import java.util.Objects;

import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public record UpdateStateRegionCommand(
        StateRegionId id,
        String name,
        String code,
        String description,
        boolean active
) {

    public UpdateStateRegionCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(code, "code must not be null");
    }
}