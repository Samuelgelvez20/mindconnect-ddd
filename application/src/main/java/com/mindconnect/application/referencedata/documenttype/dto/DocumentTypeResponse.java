package com.mindconnect.application.referencedata.documenttype.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;

public record DocumentTypeResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static DocumentTypeResponse from(DocumentType documentType) {
        return new DocumentTypeResponse(
                documentType.id().value(),
                documentType.code(),
                documentType.name(),
                documentType.active(),
                documentType.createdAt(),
                documentType.updatedAt()
        );
    }
}