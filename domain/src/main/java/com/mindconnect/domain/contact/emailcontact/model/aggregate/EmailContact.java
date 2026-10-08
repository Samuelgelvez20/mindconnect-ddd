package com.mindconnect.domain.contact.emailcontact.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.contact.emailcontact.event.EmailContactDeletedEvent;
import com.mindconnect.domain.contact.emailcontact.event.EmailContactRegisteredEvent;
import com.mindconnect.domain.contact.emailcontact.event.EmailContactUpdatedEvent;
import com.mindconnect.domain.contact.emailcontact.exception.InvalidEmailContactException;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public class EmailContact extends AggregateRoot {

    public static final int EMAIL_MAX_LENGTH = 150;
    public static final int NOTES_MAX_LENGTH = 65535;

    private final EmailContactId id;
    private ContactId contactId;
    private String email;
    private String notes;
    private final Instant createdAt;
    private Instant updatedAt;

    private EmailContact(
            EmailContactId id,
            ContactId contactId,
            String email,
            String notes,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.contactId = Objects.requireNonNull(contactId, "contactId must not be null");
        this.email = email;
        this.notes = notes;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static EmailContact register(
            ContactId contactId,
            String email,
            String notes) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        EmailContactId id = EmailContactId.generate();

        EmailContact emailContact = new EmailContact(
                id,
                contactId,
                requiredText(email, "email", EMAIL_MAX_LENGTH),
                optionalText(notes, "notes", NOTES_MAX_LENGTH),
                now,
                now);

        emailContact.recordEvent(new EmailContactRegisteredEvent(id, now));
        return emailContact;
    }

    public static EmailContact restore(
            EmailContactId id,
            ContactId contactId,
            String email,
            String notes,
            Instant createdAt,
            Instant updatedAt) {

        return new EmailContact(id, contactId, email, notes, createdAt, updatedAt);
    }

    public void update(
            String email,
            String notes) {

        this.email = requiredText(email, "email", EMAIL_MAX_LENGTH);
        this.notes = optionalText(notes, "notes", NOTES_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new EmailContactUpdatedEvent(this.id, this.email, this.updatedAt));
    }

    public void delete() {
        recordEvent(new EmailContactDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public EmailContactId id() {
        return id;
    }

    public ContactId contactId() {
        return contactId;
    }

    public String email() {
        return email;
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
            throw new InvalidEmailContactException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidEmailContactException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidEmailContactException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    }