package com.mindconnect.application.referencedata.gender.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;

public record GenderResponse(
        UUID id,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

    public static GenderResponse from(Gender gender) {
        return new GenderResponse(
                gender.id().value(),
                gender.description(),
                gender.createdAt(),
                gender.updatedAt()
        );
    }
}