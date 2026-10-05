package com.mindconnect.application.contact.phonecontact.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.contact.phonecontact.model.aggregate.PhoneContact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public record PhoneContactResponse(
        UUID id,
        UUID contactId,
        String phone,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {

    public static PhoneContactResponse from(PhoneContact phoneContact) {
        return new PhoneContactResponse(
                phoneContact.id().value(),
                phoneContact.contactId().value(),
                phoneContact.phone(),
                phoneContact.notes(),
                phoneContact.createdAt(),
                phoneContact.updatedAt()
        );
    }
}