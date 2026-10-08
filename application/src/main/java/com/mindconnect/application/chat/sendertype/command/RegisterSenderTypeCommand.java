package com.mindconnect.application.chat.sendertype.command;

import java.util.Objects;

public record RegisterSenderTypeCommand(String name) {

    public RegisterSenderTypeCommand {
        Objects.requireNonNull(name, "name must not be null");
    }
}