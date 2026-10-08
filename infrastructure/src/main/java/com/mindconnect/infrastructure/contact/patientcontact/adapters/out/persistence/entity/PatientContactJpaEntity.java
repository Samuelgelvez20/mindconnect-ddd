package com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Mirrors table patient_contacts (migration V12) exactly. */
@Entity
@Table(name = "patient_contacts")
public class PatientContactJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "contact_id", nullable = false)
    private UUID contactId;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "is_primary_contact", nullable = false)
    private boolean isPrimaryContact;

    @Column(name = "is_emergency_contact", nullable = false)
    private boolean isEmergencyContact;

    @Column(name = "relationship_type_id", nullable = false)
    private UUID relationshipTypeId;

    public PatientContactJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public boolean isPrimaryContact() {
        return isPrimaryContact;
    }

    public void setPrimaryContact(boolean primaryContact) {
        isPrimaryContact = primaryContact;
    }

    public boolean isEmergencyContact() {
        return isEmergencyContact;
    }

    public void setEmergencyContact(boolean emergencyContact) {
        isEmergencyContact = emergencyContact;
    }

    public UUID getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(UUID relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }
}