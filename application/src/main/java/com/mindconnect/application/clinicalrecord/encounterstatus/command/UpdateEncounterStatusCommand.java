package com.mindconnect.application.clinicalrecord.encounterstatus.command;

import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;

public record UpdateEncounterStatusCommand(
        EncounterStatusId id,
        String code,
        String name,
        Boolean active) {

    public UpdateEncounterStatusCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(active, "active must not be null");
    }
}