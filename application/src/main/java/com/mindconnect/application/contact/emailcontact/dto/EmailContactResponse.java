package com.mindconnect.application.contact.emailcontact.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public record EmailContactResponse(
        UUID id,
        UUID contactId,
        String email,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {

    public static EmailContactResponse from(EmailContact emailContact) {
        return new EmailContactResponse(
                emailContact.id().value(),
                emailContact.contactId().value(),
                emailContact.email(),
                emailContact.notes(),
                emailContact.createdAt(),
                emailContact.updatedAt()
        );
    }
}