package com.mindconnect.application.clinicalrecord.risklevel.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;

public record RiskLevelResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        int severity,
        Instant createdAt,
        Instant updatedAt
) {

    public static RiskLevelResponse from(RiskLevel level) {
        return new RiskLevelResponse(
                level.id().value(),
                level.code(),
                level.name(),
                level.active(),
                level.severity(),
                level.createdAt(),
                level.updatedAt()
        );
    }
}