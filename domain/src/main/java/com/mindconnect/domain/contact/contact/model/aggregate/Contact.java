package com.mindconnect.domain.contact.contact.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.contact.contact.event.ContactDeletedEvent;
import com.mindconnect.domain.contact.contact.event.ContactRegisteredEvent;
import com.mindconnect.domain.contact.contact.event.ContactUpdatedEvent;
import com.mindconnect.domain.contact.contact.exception.InvalidContactException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

public class Contact extends AggregateRoot {

    public static final int FULL_NAME_MAX_LENGTH = 200;
    public static final int EMAIL_MAX_LENGTH = 150;

    private final ContactId id;
    private String fullName;
    private String email;
    private String notes;
    private CityMunicipalityId cityId;
    private ProfessionalId createdBy;
    private ProfessionalId updatedBy;
    private final Instant createdAt;
    private Instant updatedAt;

    private Contact(
            ContactId id,
            String fullName,
            String email,
            String notes,
            CityMunicipalityId cityId,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.fullName = fullName;
        this.email = email;
        this.notes = notes;
        this.cityId = cityId;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static Contact register(
            String fullName,
            String email,
            String notes,
            CityMunicipalityId cityId,
            ProfessionalId createdBy) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ContactId id = ContactId.generate();

        if (cityId == null) {
            throw new InvalidContactException("cityId must not be null");
        }
        if (createdBy == null) {
            throw new InvalidContactException("createdBy must not be null");
        }

        Contact contact = new Contact(
                id,
                requiredText(fullName, "fullName", FULL_NAME_MAX_LENGTH),
                optionalText(email, "email", EMAIL_MAX_LENGTH),
                optionalText(notes, "notes", Integer.MAX_VALUE),
                cityId,
                createdBy,
                null,
                now,
                now);

        contact.recordEvent(new ContactRegisteredEvent(id, now));
        return contact;
    }

    public static Contact restore(
            ContactId id,
            String fullName,
            String email,
            String notes,
            CityMunicipalityId cityId,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            Instant createdAt,
            Instant updatedAt) {

        return new Contact(id, fullName, email, notes, cityId, createdBy, updatedBy, createdAt, updatedAt);
    }

    public void update(
            String fullName,
            String email,
            String notes,
            CityMunicipalityId cityId,
            ProfessionalId updatedBy) {

        if (cityId == null) {
            throw new InvalidContactException("cityId must not be null");
        }
        if (updatedBy == null) {
            throw new InvalidContactException("updatedBy must not be null");
        }

        this.fullName = requiredText(fullName, "fullName", FULL_NAME_MAX_LENGTH);
        this.email = optionalText(email, "email", EMAIL_MAX_LENGTH);
        this.notes = optionalText(notes, "notes", Integer.MAX_VALUE);
        this.cityId = cityId;
        this.updatedBy = updatedBy;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ContactUpdatedEvent(this.id, this.fullName, this.email, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ContactDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ContactId id() {
        return id;
    }

    public String fullName() {
        return fullName;
    }

    public String email() {
        return email;
    }

    public String notes() {
        return notes;
    }

    public CityMunicipalityId cityId() {
        return cityId;
    }

    public ProfessionalId createdBy() {
        return createdBy;
    }

    public ProfessionalId updatedBy() {
        return updatedBy;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidContactException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidContactException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidContactException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}