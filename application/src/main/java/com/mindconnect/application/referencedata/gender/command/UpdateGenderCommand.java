package com.mindconnect.application.referencedata.gender.command;

import java.util.Objects;

import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public record UpdateGenderCommand(
        GenderId id,
        String description) {

    public UpdateGenderCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(description, "description must not be null");
    }
}