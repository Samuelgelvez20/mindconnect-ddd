package com.mindconnect.application.chat.chatconversationstatus.command;

import java.util.Objects;

import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;

public record UpdateChatConversationStatusCommand(
        ChatConversationStatusId id,
        String name,
        boolean active) {

    public UpdateChatConversationStatusCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}