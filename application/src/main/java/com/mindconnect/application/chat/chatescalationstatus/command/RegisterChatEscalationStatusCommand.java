package com.mindconnect.application.chat.chatescalationstatus.command;

import java.util.Objects;

public record RegisterChatEscalationStatusCommand(String name) {

    public RegisterChatEscalationStatusCommand {
        Objects.requireNonNull(name, "name must not be null");
    }
}