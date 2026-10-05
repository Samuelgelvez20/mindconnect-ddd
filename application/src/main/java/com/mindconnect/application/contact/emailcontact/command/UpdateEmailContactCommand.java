package com.mindconnect.application.contact.emailcontact.command;

import java.util.Objects;

import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;

public record UpdateEmailContactCommand(
        EmailContactId id,
        String email,
        String notes) {

    public UpdateEmailContactCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(email, "email must not be null");
    }
}