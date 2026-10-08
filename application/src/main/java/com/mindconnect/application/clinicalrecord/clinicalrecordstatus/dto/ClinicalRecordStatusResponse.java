package com.mindconnect.application.clinicalrecord.clinicalrecordstatus.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;

public record ClinicalRecordStatusResponse(
        UUID id,
        String code,
        String name,
        Instant createdAt,
        Instant updatedAt
) {

    public static ClinicalRecordStatusResponse from(ClinicalRecordStatus status) {
        return new ClinicalRecordStatusResponse(
                status.id().value(),
                status.code(),
                status.name(),
                status.createdAt(),
                status.updatedAt()
        );
    }
}