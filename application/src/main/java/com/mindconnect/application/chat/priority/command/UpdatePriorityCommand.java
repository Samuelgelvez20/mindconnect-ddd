package com.mindconnect.application.chat.priority.command;

import java.util.Objects;

import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public record UpdatePriorityCommand(
        PriorityId id,
        String name,
        boolean active) {

    public UpdatePriorityCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}