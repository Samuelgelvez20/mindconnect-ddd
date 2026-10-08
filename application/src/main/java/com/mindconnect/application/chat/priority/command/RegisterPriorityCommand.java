package com.mindconnect.application.chat.priority.command;

import java.util.Objects;

public record RegisterPriorityCommand(String name) {

    public RegisterPriorityCommand {
        Objects.requireNonNull(name, "name must not be null");
    }
}