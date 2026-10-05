package com.mindconnect.application.professional.study.command;

import java.util.Objects;

import com.mindconnect.domain.professional.study.model.valueobject.StudyId;

public record UpdateStudyCommand(
        StudyId id,
        String name) {

    public UpdateStudyCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
    }
}