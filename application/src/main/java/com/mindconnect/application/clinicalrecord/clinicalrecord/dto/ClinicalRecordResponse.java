package com.mindconnect.application.clinicalrecord.clinicalrecord.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate.ClinicalRecord;

public record ClinicalRecordResponse(
        UUID id,
        UUID patientId,
        Instant creationDate,
        String recordNumber,
        Instant openedAt,
        Instant closedAt,
        UUID statusId,
        UUID createdBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static ClinicalRecordResponse from(ClinicalRecord record) {
        return new ClinicalRecordResponse(
                record.id().value(),
                record.patientId().value(),
                record.creationDate(),
                record.recordNumber(),
                record.openedAt(),
                record.closedAt(),
                record.statusId().value(),
                record.createdBy().value(),
                record.createdAt(),
                record.updatedAt()
        );
    }
}