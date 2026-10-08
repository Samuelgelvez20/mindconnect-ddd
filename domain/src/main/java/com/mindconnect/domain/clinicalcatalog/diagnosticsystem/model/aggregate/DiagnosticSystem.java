package com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event.DiagnosticSystemDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event.DiagnosticSystemRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event.DiagnosticSystemUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.exception.InvalidDiagnosticSystemException;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;

public class DiagnosticSystem extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;
    public static final int VERSION_MAX_LENGTH = 20;

    private final DiagnosticSystemId id;
    private String code;
    private String name;
    private String version;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private DiagnosticSystem(
            DiagnosticSystemId id,
            String code,
            String name,
            String version,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.code = code;
        this.name = name;
        this.version = version;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static DiagnosticSystem register(String code, String name, String version) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        DiagnosticSystemId id = DiagnosticSystemId.generate();

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);
        String normalizedVersion = normalizeVersion(version);

        DiagnosticSystem system = new DiagnosticSystem(
                id,
                requiredCode,
                requiredName,
                normalizedVersion,
                true,
                now,
                now);

        system.recordEvent(new DiagnosticSystemRegisteredEvent(id, now));
        return system;
    }

    public static DiagnosticSystem restore(
            DiagnosticSystemId id,
            String code,
            String name,
            String version,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new DiagnosticSystem(id, code, name, version, active, createdAt, updatedAt);
    }

    public void update(String code, String name, String version, boolean active) {

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);
        String normalizedVersion = normalizeVersion(version);

        this.code = requiredCode;
        this.name = requiredName;
        this.version = normalizedVersion;
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new DiagnosticSystemUpdatedEvent(this.id, this.code, this.name, this.version, this.active, this.updatedAt));
    }

    public void delete() {
        recordEvent(new DiagnosticSystemDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public DiagnosticSystemId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String version() {
        return version;
    }

    public boolean isActive() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidDiagnosticSystemException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidDiagnosticSystemException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String normalizeVersion(String version) {
        if (version == null) {
            return null;
        }
        String trimmed = version.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        if (trimmed.length() > VERSION_MAX_LENGTH) {
            throw new InvalidDiagnosticSystemException("version must have at most " + VERSION_MAX_LENGTH + " characters");
        }
        return trimmed;
    }
}