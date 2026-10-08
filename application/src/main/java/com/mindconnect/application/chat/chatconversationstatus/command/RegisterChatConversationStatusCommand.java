package com.mindconnect.application.chat.chatconversationstatus.command;

import java.util.Objects;

public record RegisterChatConversationStatusCommand(String name) {

    public RegisterChatConversationStatusCommand {
        Objects.requireNonNull(name, "name must not be null");
    }
}