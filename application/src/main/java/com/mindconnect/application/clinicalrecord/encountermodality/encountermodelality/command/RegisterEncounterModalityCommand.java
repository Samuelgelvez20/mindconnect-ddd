package com.mindconnect.application.clinicalrecord.encountermodelality.command;

import java.util.Objects;

public record RegisterEncounterModalityCommand(
        String code,
        String name) {

    public RegisterEncounterModalityCommand {
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}