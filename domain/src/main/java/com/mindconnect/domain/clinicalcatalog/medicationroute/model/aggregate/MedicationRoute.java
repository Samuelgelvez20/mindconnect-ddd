package com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalcatalog.medicationroute.event.MedicationRouteDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.medicationroute.event.MedicationRouteRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.medicationroute.event.MedicationRouteUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.medicationroute.exception.InvalidMedicationRouteException;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;

public class MedicationRoute extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final MedicationRouteId id;
    private String code;
    private String name;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private MedicationRoute(
            MedicationRouteId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.code = code;
        this.name = name;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static MedicationRoute register(String code, String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        MedicationRouteId id = MedicationRouteId.generate();

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);

        MedicationRoute route = new MedicationRoute(
                id,
                requiredCode,
                requiredName,
                true,
                now,
                now);

        route.recordEvent(new MedicationRouteRegisteredEvent(id, now));
        return route;
    }

    public static MedicationRoute restore(
            MedicationRouteId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new MedicationRoute(id, code, name, active, createdAt, updatedAt);
    }

    public void update(String code, String name, boolean active) {

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);

        this.code = requiredCode;
        this.name = requiredName;
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new MedicationRouteUpdatedEvent(this.id, this.code, this.name, this.active, this.updatedAt));
    }

    public void delete() {
        recordEvent(new MedicationRouteDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public MedicationRouteId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
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
            throw new InvalidMedicationRouteException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidMedicationRouteException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}