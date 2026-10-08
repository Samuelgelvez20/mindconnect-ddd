package com.mindconnect.domain.referencedata.citymunicipality.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.referencedata.citymunicipality.event.CityMunicipalityDeletedEvent;
import com.mindconnect.domain.referencedata.citymunicipality.event.CityMunicipalityRegisteredEvent;
import com.mindconnect.domain.referencedata.citymunicipality.event.CityMunicipalityUpdatedEvent;
import com.mindconnect.domain.referencedata.citymunicipality.exception.InvalidCityMunicipalityException;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public class CityMunicipality extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int CODE_MAX_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 100;

    private final CityMunicipalityId id;
    private String name;
    private String code;
    private String description;
    private StateRegionId regionId;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private CityMunicipality(
            CityMunicipalityId id,
            String name,
            String code,
            String description,
            StateRegionId regionId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.code = code;
        this.description = description;
        this.regionId = Objects.requireNonNull(regionId, "regionId must not be null");
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static CityMunicipality register(
            String name,
            String code,
            String description,
            StateRegionId regionId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        CityMunicipalityId id = CityMunicipalityId.generate();

        if (regionId == null) {
            throw new InvalidCityMunicipalityException("regionId must not be null");
        }

        CityMunicipality cityMunicipality = new CityMunicipality(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                requiredText(code, "code", CODE_MAX_LENGTH),
                optionalText(description, "description", DESCRIPTION_MAX_LENGTH),
                regionId,
                true,
                now,
                now);

        cityMunicipality.recordEvent(new CityMunicipalityRegisteredEvent(id, now));
        return cityMunicipality;
    }

    public static CityMunicipality restore(
            CityMunicipalityId id,
            String name,
            String code,
            String description,
            StateRegionId regionId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new CityMunicipality(id, name, code, description, regionId, active, createdAt, updatedAt);
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

        recordEvent(new CityMunicipalityUpdatedEvent(this.id, this.name, this.code, this.updatedAt));
    }

    public void delete() {
        recordEvent(new CityMunicipalityDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public CityMunicipalityId id() {
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

    public StateRegionId regionId() {
        return regionId;
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
            throw new InvalidCityMunicipalityException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidCityMunicipalityException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidCityMunicipalityException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}