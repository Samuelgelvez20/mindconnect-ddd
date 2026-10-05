package com.mindconnect.domain.contact.patientcontact.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.contact.patientcontact.event.PatientContactDeletedEvent;
import com.mindconnect.domain.contact.patientcontact.event.PatientContactRegisteredEvent;
import com.mindconnect.domain.contact.patientcontact.event.PatientContactUpdatedEvent;
import com.mindconnect.domain.contact.patientcontact.exception.InvalidPatientContactException;
import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

public class PatientContact extends AggregateRoot {

    public static final int NOTES_MAX_LENGTH = 65535;

    private final PatientContactId id;
    private ContactId contactId;
    private PatientId patientId;
    private boolean isPrimaryContact;
    private boolean isEmergencyContact;
    private RelationshipTypeId relationshipTypeId;
    private final Instant createdAt;
    private Instant updatedAt;

    private PatientContact(
            PatientContactId id,
            ContactId contactId,
            PatientId patientId,
            boolean isPrimaryContact,
            boolean isEmergencyContact,
            RelationshipTypeId relationshipTypeId,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.contactId = contactId;
        this.patientId = patientId;
        this.isPrimaryContact = isPrimaryContact;
        this.isEmergencyContact = isEmergencyContact;
        this.relationshipTypeId = relationshipTypeId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static PatientContact register(
            ContactId contactId,
            PatientId patientId,
            RelationshipTypeId relationshipTypeId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        PatientContactId id = PatientContactId.generate();

        PatientContact patientContact = new PatientContact(
                id,
                contactId,
                patientId,
                false,
                false,
                relationshipTypeId,
                now,
                now);

        patientContact.recordEvent(new PatientContactRegisteredEvent(id, now));
        return patientContact;
    }

    public static PatientContact restore(
            PatientContactId id,
            ContactId contactId,
            PatientId patientId,
            boolean isPrimaryContact,
            boolean isEmergencyContact,
            RelationshipTypeId relationshipTypeId,
            Instant createdAt,
            Instant updatedAt) {

        return new PatientContact(id, contactId, patientId, false, false, relationshipTypeId, createdAt, updatedAt);
    }

    public void update(
            boolean isPrimaryContact,
            boolean isEmergencyContact,
            RelationshipTypeId relationshipTypeId) {

        this.isPrimaryContact = isPrimaryContact;
        this.isEmergencyContact = isEmergencyContact;
        this.relationshipTypeId = relationshipTypeId;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new PatientContactUpdatedEvent(this.id, this.updatedAt));
    }

    public void delete() {
        recordEvent(new PatientContactDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public PatientContactId id() {
        return id;
    }

    public ContactId contactId() {
        return contactId;
    }

    public PatientId patientId() {
        return patientId;
    }

    public boolean isPrimaryContact() {
        return isPrimaryContact;
    }

    public boolean isEmergencyContact() {
        return isEmergencyContact;
    }

    public RelationshipTypeId relationshipTypeId() {
        return relationshipTypeId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}