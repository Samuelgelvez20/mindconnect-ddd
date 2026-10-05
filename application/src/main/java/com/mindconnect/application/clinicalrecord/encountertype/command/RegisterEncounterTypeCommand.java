package com.mindconnect.application.clinicalrecord.encountertype.command;

import java.util.Objects;

public record RegisterEncounterTypeCommand(
        String code,
        String name) {

    public RegisterEncounterTypeCommand {
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}