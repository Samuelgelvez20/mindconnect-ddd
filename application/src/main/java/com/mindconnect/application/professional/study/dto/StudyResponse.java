package com.mindconnect.application.professional.study.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.professional.study.model.aggregate.Study;

public record StudyResponse(
        UUID id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {

    public static StudyResponse from(Study study) {
        return new StudyResponse(
                study.id().value(),
                study.name(),
                study.createdAt(),
                study.updatedAt()
        );
    }
}