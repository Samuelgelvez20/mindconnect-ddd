package com.mindconnect.application.contact.phonecontact.command;

import java.util.Objects;

import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public record RegisterPhoneContactCommand(
        ContactId contactId,
        String phone,
        String notes) {

    public RegisterPhoneContactCommand {
        Objects.requireNonNull(contactId, "contactId must not be null");
        Objects.requireNonNull(phone, "phone must not be null");
    }
}