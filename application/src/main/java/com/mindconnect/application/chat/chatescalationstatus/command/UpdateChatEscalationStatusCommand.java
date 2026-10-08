package com.mindconnect.application.chat.chatescalationstatus.command;

import java.util.Objects;

import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record UpdateChatEscalationStatusCommand(
        ChatEscalationStatusId id,
        String name,
        boolean active) {

    public UpdateChatEscalationStatusCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}