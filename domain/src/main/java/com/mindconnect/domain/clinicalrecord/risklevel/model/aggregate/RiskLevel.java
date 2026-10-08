package com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.risklevel.event.RiskLevelDeletedEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.event.RiskLevelRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.event.RiskLevelUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.exception.InvalidRiskLevelException;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;

public class RiskLevel extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final RiskLevelId id;
    private String code;
    private String name;
    private boolean active;
    private int severity;
    private final Instant createdAt;
    private Instant updatedAt;

    private RiskLevel(
            RiskLevelId id,
            String code,
            String name,
            boolean active,
            int severity,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.code = code;
        this.name = name;
        this.active = active;
        this.severity = severity;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static RiskLevel register(String code, String name, int severity) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        RiskLevelId id = RiskLevelId.generate();

        RiskLevel level = new RiskLevel(
                id,
                requiredText(code, "code", CODE_MAX_LENGTH),
                requiredText(name, "name", NAME_MAX_LENGTH),
                true,
                severity,
                now,
                now);

        level.recordEvent(new RiskLevelRegisteredEvent(id, now));
        return level;
    }

    public static RiskLevel restore(
            RiskLevelId id,
            String code,
            String name,
            boolean active,
            int severity,
            Instant createdAt,
            Instant updatedAt) {

        return new RiskLevel(id, code, name, active, severity, createdAt, updatedAt);
    }

    public void update(String code, String name, boolean active, int severity) {

        this.code = requiredText(code, "code", CODE_MAX_LENGTH);
        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.active = active;
        this.severity = severity;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new RiskLevelUpdatedEvent(this.id, this.code, this.name, this.active, this.severity, this.updatedAt));
    }

    public void delete() {
        recordEvent(new RiskLevelDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public RiskLevelId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public boolean active() {
        return active;
    }

    public int severity() {
        return severity;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidRiskLevelException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidRiskLevelException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}