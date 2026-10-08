package com.mindconnect.application.clinicalrecord.encounterstatus.command;

import java.util.Objects;

public record RegisterEncounterStatusCommand(
        String code,
        String name) {

    public RegisterEncounterStatusCommand {
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}