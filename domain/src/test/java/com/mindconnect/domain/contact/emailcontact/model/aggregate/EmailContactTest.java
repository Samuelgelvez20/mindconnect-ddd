package com.mindconnect.domain.contact.emailcontact.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.contact.emailcontact.event.EmailContactDeletedEvent;
import com.mindconnect.domain.contact.emailcontact.event.EmailContactRegisteredEvent;
import com.mindconnect.domain.contact.emailcontact.event.EmailContactUpdatedEvent;
import com.mindconnect.domain.contact.emailcontact.exception.InvalidEmailContactException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

class EmailContactTest {

    @Test
    void shouldRegisterEmailContactAndRecordEvent() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "juan@example.com", "Personal");

        assertNotNull(emailContact.id());
        assertEquals("juan@example.com", emailContact.email());
        assertEquals("Personal", emailContact.notes());
        assertEquals(emailContact.createdAt(), emailContact.updatedAt());

        assertEquals(1, emailContact.domainEvents().size());
        EmailContactRegisteredEvent event = assertInstanceOf(
                EmailContactRegisteredEvent.class, emailContact.domainEvents().getFirst());
        assertEquals(emailContact.id(), event.id());
    }

    @Test
    void shouldTrimEmailAndNullifyOptionalBlanks() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "  juan@example.com  ", "   ");

        assertEquals("juan@example.com", emailContact.email());
        assertNull(emailContact.notes());
    }

    @Test
    void shouldRejectBlankEmail() {
        assertThrows(InvalidEmailContactException.class, () -> EmailContact.register(ContactId.generate(), "  ", null));
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(InvalidEmailContactException.class, () -> EmailContact.register(ContactId.generate(), null, null));
    }

    @Test
    void shouldRejectTooLongEmail() {
        assertThrows(InvalidEmailContactException.class,
                () -> EmailContact.register(ContactId.generate(), "x".repeat(EmailContact.EMAIL_MAX_LENGTH + 1) + "@example.com", null));
    }

    @Test
    void shouldRejectTooLongNotes() {
        assertThrows(InvalidEmailContactException.class,
                () -> EmailContact.register(ContactId.generate(), "juan@example.com", "x".repeat(EmailContact.NOTES_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "juan@example.com", "Personal");
        emailContact.clearDomainEvents();

        emailContact.update("maria@example.com", "Work");

        assertEquals("maria@example.com", emailContact.email());
        assertEquals("Work", emailContact.notes());
        assertFalse(emailContact.updatedAt().isBefore(emailContact.createdAt()));

        assertEquals(1, emailContact.domainEvents().size());
        EmailContactUpdatedEvent event = assertInstanceOf(
                EmailContactUpdatedEvent.class, emailContact.domainEvents().getFirst());
        assertEquals("maria@example.com", event.email());
    }

    @Test
    void shouldNullifyBlankNotesOnUpdate() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "juan@example.com", "Personal");
        emailContact.clearDomainEvents();

        emailContact.update("maria@example.com", "  ");

        assertEquals("maria@example.com", emailContact.email());
        assertNull(emailContact.notes());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "juan@example.com", "Personal");
        emailContact.clearDomainEvents();

        assertThrows(InvalidEmailContactException.class, () -> emailContact.update("", "Work"));
        assertTrue(emailContact.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "juan@example.com", "Personal");
        emailContact.clearDomainEvents();

        emailContact.delete();

        assertEquals(1, emailContact.domainEvents().size());
        EmailContactDeletedEvent event = assertInstanceOf(
                EmailContactDeletedEvent.class, emailContact.domainEvents().getLast());
        assertEquals(emailContact.id(), event.id());
    }
}