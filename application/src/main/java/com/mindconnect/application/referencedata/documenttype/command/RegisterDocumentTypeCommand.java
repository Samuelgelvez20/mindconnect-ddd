package com.mindconnect.application.referencedata.documenttype.command;

import java.util.Objects;

public record RegisterDocumentTypeCommand(
        String code,
        String name
) {

    public RegisterDocumentTypeCommand {
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}