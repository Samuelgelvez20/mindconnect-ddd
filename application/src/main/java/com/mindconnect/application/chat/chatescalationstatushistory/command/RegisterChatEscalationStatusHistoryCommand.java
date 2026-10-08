package com.mindconnect.application.chat.chatescalationstatushistory.command;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record RegisterChatEscalationStatusHistoryCommand(
        ChatEscalationId escalationId,
        ChatEscalationStatusId escalationStatusId,
        Instant changedAt) {

    public RegisterChatEscalationStatusHistoryCommand {
        Objects.requireNonNull(escalationId, "escalationId must not be null");
        Objects.requireNonNull(escalationStatusId, "escalationStatusId must not be null");
        Objects.requireNonNull(changedAt, "changedAt must not be null");
    }
}