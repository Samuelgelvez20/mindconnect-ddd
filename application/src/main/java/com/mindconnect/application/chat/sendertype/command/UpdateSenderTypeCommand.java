package com.mindconnect.application.chat.sendertype.command;

import java.util.Objects;

import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;

public record UpdateSenderTypeCommand(
        SenderTypeId id,
        String name,
        boolean active) {

    public UpdateSenderTypeCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}