package com.mindconnect.domain.contact.patientcontact.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.contact.patientcontact.event.PatientContactDeletedEvent;
import com.mindconnect.domain.contact.patientcontact.event.PatientContactRegisteredEvent;
import com.mindconnect.domain.contact.patientcontact.event.PatientContactUpdatedEvent;
import com.mindconnect.domain.contact.patientcontact.exception.InvalidPatientContactException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

class PatientContactTest {

    @Test
    void shouldRegisterPatientContactAndRecordEvent() {
        ContactId contactId = ContactId.generate();
        PatientId patientId = PatientId.generate();
        RelationshipTypeId relationshipTypeId = RelationshipTypeId.generate();

        PatientContact patientContact = PatientContact.register(contactId, patientId, relationshipTypeId);

        assertNotNull(patientContact.id());
        assertEquals(contactId, patientContact.contactId());
        assertEquals(patientId, patientContact.patientId());
        assertEquals(relationshipTypeId, patientContact.relationshipTypeId());
        assertFalse(patientContact.isPrimaryContact());
        assertFalse(patientContact.isEmergencyContact());
        assertEquals(patientContact.createdAt(), patientContact.updatedAt());

        assertEquals(1, patientContact.domainEvents().size());
        PatientContactRegisteredEvent event = assertInstanceOf(
                PatientContactRegisteredEvent.class, patientContact.domainEvents().getFirst());
        assertEquals(patientContact.id(), event.id());
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        ContactId contactId = ContactId.generate();
        PatientId patientId = PatientId.generate();
        RelationshipTypeId relationshipTypeId = RelationshipTypeId.generate();

        PatientContact patientContact = PatientContact.register(contactId, patientId, relationshipTypeId);
        patientContact.clearDomainEvents();

        RelationshipTypeId newRelationshipTypeId = RelationshipTypeId.generate();

        patientContact.update(true, true, newRelationshipTypeId);

        assertTrue(patientContact.isPrimaryContact());
        assertTrue(patientContact.isEmergencyContact());
        assertEquals(newRelationshipTypeId, patientContact.relationshipTypeId());
        assertFalse(patientContact.updatedAt().isBefore(patientContact.createdAt()));

        assertEquals(1, patientContact.domainEvents().size());
        PatientContactUpdatedEvent event = assertInstanceOf(
                PatientContactUpdatedEvent.class, patientContact.domainEvents().getFirst());
        assertEquals(patientContact.id(), event.id());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        ContactId contactId = ContactId.generate();
        PatientId patientId = PatientId.generate();
        RelationshipTypeId relationshipTypeId = RelationshipTypeId.generate();

        PatientContact patientContact = PatientContact.register(contactId, patientId, relationshipTypeId);
        patientContact.clearDomainEvents();

        patientContact.delete();

        assertEquals(1, patientContact.domainEvents().size());
        PatientContactDeletedEvent event = assertInstanceOf(
                PatientContactDeletedEvent.class, patientContact.domainEvents().getLast());
        assertEquals(patientContact.id(), event.id());
    }
}