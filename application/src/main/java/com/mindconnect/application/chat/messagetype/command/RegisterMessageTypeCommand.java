package com.mindconnect.application.chat.messagetype.command;

import java.util.Objects;

public record RegisterMessageTypeCommand(String name) {

    public RegisterMessageTypeCommand {
        Objects.requireNonNull(name, "name must not be null");
    }
}