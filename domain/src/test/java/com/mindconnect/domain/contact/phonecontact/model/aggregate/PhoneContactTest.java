package com.mindconnect.domain.contact.phonecontact.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.contact.phonecontact.event.PhoneContactDeletedEvent;
import com.mindconnect.domain.contact.phonecontact.event.PhoneContactRegisteredEvent;
import com.mindconnect.domain.contact.phonecontact.event.PhoneContactUpdatedEvent;
import com.mindconnect.domain.contact.phonecontact.exception.InvalidPhoneContactException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

class PhoneContactTest {

    @Test
    void shouldRegisterPhoneContactAndRecordEvent() {
        PhoneContact phoneContact = PhoneContact.register(ContactId.generate(), "3001234567", "Mobile");

        assertNotNull(phoneContact.id());
        assertEquals("3001234567", phoneContact.phone());
        assertEquals("Mobile", phoneContact.notes());
        assertEquals(phoneContact.createdAt(), phoneContact.updatedAt());

        assertEquals(1, phoneContact.domainEvents().size());
        PhoneContactRegisteredEvent event = assertInstanceOf(
                PhoneContactRegisteredEvent.class, phoneContact.domainEvents().getFirst());
        assertEquals(phoneContact.id(), event.id());
    }

    @Test
    void shouldTrimPhoneAndNullifyOptionalBlanks() {
        PhoneContact phoneContact = PhoneContact.register(ContactId.generate(), "  3001234567  ", "   ");

        assertEquals("3001234567", phoneContact.phone());
        assertNull(phoneContact.notes());
    }

    @Test
    void shouldRejectBlankPhone() {
        assertThrows(InvalidPhoneContactException.class, () -> PhoneContact.register(ContactId.generate(), "  ", null));
    }

    @Test
    void shouldRejectNullPhone() {
        assertThrows(InvalidPhoneContactException.class, () -> PhoneContact.register(ContactId.generate(), null, null));
    }

    @Test
    void shouldRejectTooLongPhone() {
        assertThrows(InvalidPhoneContactException.class,
                () -> PhoneContact.register(ContactId.generate(), "x".repeat(PhoneContact.PHONE_MAX_LENGTH + 1), null));
    }

    @Test
    void shouldRejectTooLongNotes() {
        assertThrows(InvalidPhoneContactException.class,
                () -> PhoneContact.register(ContactId.generate(), "3001234567", "x".repeat(PhoneContact.NOTES_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        PhoneContact phoneContact = PhoneContact.register(ContactId.generate(), "3001234567", null);
        phoneContact.clearDomainEvents();

        phoneContact.update("3109876543", "Work");

        assertEquals("3109876543", phoneContact.phone());
        assertEquals("Work", phoneContact.notes());
        assertFalse(phoneContact.updatedAt().isBefore(phoneContact.createdAt()));

        assertEquals(1, phoneContact.domainEvents().size());
        PhoneContactUpdatedEvent event = assertInstanceOf(
                PhoneContactUpdatedEvent.class, phoneContact.domainEvents().getFirst());
        assertEquals("3109876543", event.phone());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        PhoneContact phoneContact = PhoneContact.register(ContactId.generate(), "3001234567", null);
        phoneContact.clearDomainEvents();

        assertThrows(InvalidPhoneContactException.class, () -> phoneContact.update("", null));
        assertTrue(phoneContact.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        PhoneContact phoneContact = PhoneContact.register(ContactId.generate(), "3001234567", null);
        phoneContact.clearDomainEvents();

        phoneContact.delete();

        assertEquals(1, phoneContact.domainEvents().size());
        PhoneContactDeletedEvent event = assertInstanceOf(
                PhoneContactDeletedEvent.class, phoneContact.domainEvents().getLast());
        assertEquals(phoneContact.id(), event.id());
    }
}