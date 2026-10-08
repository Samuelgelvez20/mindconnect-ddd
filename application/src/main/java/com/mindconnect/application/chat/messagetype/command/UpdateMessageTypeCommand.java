package com.mindconnect.application.chat.messagetype.command;

import java.util.Objects;

import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;

public record UpdateMessageTypeCommand(
        MessageTypeId id,
        String name,
        boolean active) {

    public UpdateMessageTypeCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}