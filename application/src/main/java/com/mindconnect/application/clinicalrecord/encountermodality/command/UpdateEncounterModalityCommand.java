package com.mindconnect.application.clinicalrecord.encountermodality.command;

import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;

public record UpdateEncounterModalityCommand(
        EncounterModalityId id,
        String code,
        String name,
        Boolean active) {

    public UpdateEncounterModalityCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(active, "active must not be null");
    }
}