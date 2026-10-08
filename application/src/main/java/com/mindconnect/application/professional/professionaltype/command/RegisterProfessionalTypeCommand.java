package com.mindconnect.application.professional.professionaltype.command;

import java.util.Objects;

public record RegisterProfessionalTypeCommand(String name) {

    public RegisterProfessionalTypeCommand {
        Objects.requireNonNull(name, "name must not be null");
    }
}