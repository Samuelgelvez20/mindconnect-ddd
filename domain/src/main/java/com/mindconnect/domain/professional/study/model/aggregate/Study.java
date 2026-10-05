package com.mindconnect.domain.professional.study.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.professional.study.event.StudyDeletedEvent;
import com.mindconnect.domain.professional.study.event.StudyRegisteredEvent;
import com.mindconnect.domain.professional.study.event.StudyUpdatedEvent;
import com.mindconnect.domain.professional.study.exception.InvalidStudyException;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;

public class Study extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 40;

    private final StudyId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private Study(
            StudyId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static Study register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        StudyId id = StudyId.generate();

        Study study = new Study(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        study.recordEvent(new StudyRegisteredEvent(id, now));
        return study;
    }

    public static Study restore(
            StudyId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new Study(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new StudyUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new StudyDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public StudyId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidStudyException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidStudyException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}