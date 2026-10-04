package com.mindconnect.domain.referencedata.stateregion.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.event.StateRegionDeletedEvent;
import com.mindconnect.domain.referencedata.stateregion.event.StateRegionRegisteredEvent;
import com.mindconnect.domain.referencedata.stateregion.event.StateRegionUpdatedEvent;
import com.mindconnect.domain.referencedata.stateregion.exception.InvalidStateRegionException;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public class StateRegion extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int CODE_MAX_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 100;

    private final StateRegionId id;
    private String name;
    private String code;
    private String description;
    private CountryId countryId;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private StateRegion(
            StateRegionId id,
            String name,
            String code,
            String description,
            CountryId countryId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.code = code;
        this.description = description;
        this.countryId = Objects.requireNonNull(countryId, "countryId must not be null");
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static StateRegion register(
            String name,
            String code,
            String description,
            CountryId countryId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        StateRegionId id = StateRegionId.generate();

        if (countryId == null) {
            throw new InvalidStateRegionException("countryId must not be null");
        }

        StateRegion stateRegion = new StateRegion(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                requiredText(code, "code", CODE_MAX_LENGTH),
                optionalText(description, "description", DESCRIPTION_MAX_LENGTH),
                countryId,
                true,
                now,
                now);

        stateRegion.recordEvent(new StateRegionRegisteredEvent(id, now));
        return stateRegion;
    }

    public static StateRegion restore(
            StateRegionId id,
            String name,
            String code,
            String description,
            CountryId countryId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new StateRegion(id, name, code, description, countryId, active, createdAt, updatedAt);
    }

    public void update(
            String name,
            String code,
            String description,
            boolean active) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.code = requiredText(code, "code", CODE_MAX_LENGTH);
        this.description = optionalText(description, "description", DESCRIPTION_MAX_LENGTH);
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new StateRegionUpdatedEvent(this.id, this.name, this.code, this.updatedAt));
    }

    public void delete() {
        recordEvent(new StateRegionDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public StateRegionId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String code() {
        return code;
    }

    public String description() {
        return description;
    }

    public CountryId countryId() {
        return countryId;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidStateRegionException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidStateRegionException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidStateRegionException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}