package com.mindconnect.domain.referencedata.country.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.referencedata.country.event.CountryDeletedEvent;
import com.mindconnect.domain.referencedata.country.event.CountryRegisteredEvent;
import com.mindconnect.domain.referencedata.country.event.CountryUpdatedEvent;
import com.mindconnect.domain.referencedata.country.exception.InvalidCountryException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public class Country extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int CODE_MAX_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 100;
    public static final int TELEPHONE_PREFIX_MAX_LENGTH = 5;

    private final CountryId id;
    private String name;
    private String code;
    private String description;
    private String telephonePrefix;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private Country(
            CountryId id,
            String name,
            String code,
            String description,
            String telephonePrefix,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.code = code;
        this.description = description;
        this.telephonePrefix = telephonePrefix;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    /** Creates a brand new country (validates invariants and records the event). */
    public static Country register(
            String name,
            String code,
            String description,
            String telephonePrefix) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        CountryId id = CountryId.generate();

        Country country = new Country(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                requiredText(code, "code", CODE_MAX_LENGTH),
                optionalText(description, "description", DESCRIPTION_MAX_LENGTH),
                optionalText(telephonePrefix, "telephonePrefix", TELEPHONE_PREFIX_MAX_LENGTH),
                true,
                now,
                now);

        country.recordEvent(new CountryRegisteredEvent(id, now));
        return country;
    }

    /** Rebuilds a country from persisted state (no validation, no events). */
    public static Country restore(
            CountryId id,
            String name,
            String code,
            String description,
            String telephonePrefix,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new Country(id, name, code, description, telephonePrefix, active, createdAt, updatedAt);
    }

    public void update(
            String name,
            String code,
            String description,
            String telephonePrefix,
            boolean active) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.code = requiredText(code, "code", CODE_MAX_LENGTH);
        this.description = optionalText(description, "description", DESCRIPTION_MAX_LENGTH);
        this.telephonePrefix = optionalText(telephonePrefix, "telephonePrefix", TELEPHONE_PREFIX_MAX_LENGTH);
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new CountryUpdatedEvent(this.id, this.name, this.code, this.updatedAt));
    }

    public void delete() {
        recordEvent(new CountryDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public CountryId id() {
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

    public String telephonePrefix() {
        return telephonePrefix;
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
            throw new InvalidCountryException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidCountryException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidCountryException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}
