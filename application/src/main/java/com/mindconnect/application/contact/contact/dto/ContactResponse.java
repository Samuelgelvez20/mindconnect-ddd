package com.mindconnect.application.contact.contact.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.contact.contact.model.aggregate.Contact;

public record ContactResponse(
        UUID id,
        String fullName,
        String email,
        String notes,
        UUID cityId,
        UUID createdBy,
        UUID updatedBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static ContactResponse from(Contact contact) {
        return new ContactResponse(
                contact.id().value(),
                contact.fullName(),
                contact.email(),
                contact.notes(),
                contact.cityId().value(),
                contact.createdBy().value(),
                contact.updatedBy() != null ? contact.updatedBy().value() : null,
                contact.createdAt(),
                contact.updatedAt()
        );
    }
}