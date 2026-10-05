package com.mindconnect.application.clinicalrecord.risklevel.command;

import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;

public record UpdateRiskLevelCommand(
        RiskLevelId id,
        String code,
        String name,
        boolean active,
        int severity) {

    public UpdateRiskLevelCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}