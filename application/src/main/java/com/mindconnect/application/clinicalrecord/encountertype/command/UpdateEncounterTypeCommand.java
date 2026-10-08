package com.mindconnect.application.clinicalrecord.encountertype.command;

import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;

public record UpdateEncounterTypeCommand(
        EncounterTypeId id,
        String code,
        String name,
        Boolean active) {

    public UpdateEncounterTypeCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(active, "active must not be null");
    }
}