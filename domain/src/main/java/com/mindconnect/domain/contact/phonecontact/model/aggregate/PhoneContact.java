package com.mindconnect.domain.contact.phonecontact.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.contact.phonecontact.event.PhoneContactDeletedEvent;
import com.mindconnect.domain.contact.phonecontact.event.PhoneContactRegisteredEvent;
import com.mindconnect.domain.contact.phonecontact.event.PhoneContactUpdatedEvent;
import com.mindconnect.domain.contact.phonecontact.exception.InvalidPhoneContactException;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public class PhoneContact extends AggregateRoot {

    public static final int PHONE_MAX_LENGTH = 30;
    public static final int NOTES_MAX_LENGTH = 65535;

    private final PhoneContactId id;
    private ContactId contactId;
    private String phone;
    private String notes;
    private final Instant createdAt;
    private Instant updatedAt;

    private PhoneContact(
            PhoneContactId id,
            ContactId contactId,
            String phone,
            String notes,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.contactId = Objects.requireNonNull(contactId, "contactId must not be null");
        this.phone = phone;
        this.notes = notes;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static PhoneContact register(
            ContactId contactId,
            String phone,
            String notes) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        PhoneContactId id = PhoneContactId.generate();

        PhoneContact phoneContact = new PhoneContact(
                id,
                contactId,
                requiredText(phone, "phone", PHONE_MAX_LENGTH),
                optionalText(notes, "notes", NOTES_MAX_LENGTH),
                now,
                now);

        phoneContact.recordEvent(new PhoneContactRegisteredEvent(id, now));
        return phoneContact;
    }

    public static PhoneContact restore(
            PhoneContactId id,
            ContactId contactId,
            String phone,
            String notes,
            Instant createdAt,
            Instant updatedAt) {

        return new PhoneContact(id, contactId, phone, notes, createdAt, updatedAt);
    }

    public void update(
            String phone,
            String notes) {

        this.phone = requiredText(phone, "phone", PHONE_MAX_LENGTH);
        this.notes = optionalText(notes, "notes", NOTES_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new PhoneContactUpdatedEvent(this.id, this.phone, this.updatedAt));
    }

    public void delete() {
        recordEvent(new PhoneContactDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public PhoneContactId id() {
        return id;
    }

    public ContactId contactId() {
        return contactId;
    }

    public String phone() {
        return phone;
    }

    public String notes() {
        return notes;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidPhoneContactException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidPhoneContactException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidPhoneContactException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}