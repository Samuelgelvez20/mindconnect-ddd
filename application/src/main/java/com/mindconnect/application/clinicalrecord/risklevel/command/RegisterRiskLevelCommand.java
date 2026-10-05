package com.mindconnect.application.clinicalrecord.risklevel.command;

import java.util.Objects;

public record RegisterRiskLevelCommand(
        String code,
        String name,
        int severity) {

    public RegisterRiskLevelCommand {
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}