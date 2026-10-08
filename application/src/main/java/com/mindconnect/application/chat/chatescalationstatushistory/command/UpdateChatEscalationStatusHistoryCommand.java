package com.mindconnect.application.chat.chatescalationstatushistory.command;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record UpdateChatEscalationStatusHistoryCommand(
        ChatEscalationStatusHistoryId id,
        ChatEscalationId escalationId,
        ChatEscalationStatusId escalationStatusId,
        Instant changedAt) {

    public UpdateChatEscalationStatusHistoryCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(escalationId, "escalationId must not be null");
        Objects.requireNonNull(escalationStatusId, "escalationStatusId must not be null");
        Objects.requireNonNull(changedAt, "changedAt must not be null");
    }
}