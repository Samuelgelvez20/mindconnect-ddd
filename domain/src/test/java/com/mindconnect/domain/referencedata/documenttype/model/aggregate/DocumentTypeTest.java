package com.mindconnect.domain.referencedata.documenttype.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.referencedata.documenttype.event.DocumentTypeDeletedEvent;
import com.mindconnect.domain.referencedata.documenttype.event.DocumentTypeRegisteredEvent;
import com.mindconnect.domain.referencedata.documenttype.event.DocumentTypeUpdatedEvent;
import com.mindconnect.domain.referencedata.documenttype.exception.InvalidDocumentTypeException;

class DocumentTypeTest {

    @Test
    void shouldRegisterActiveDocumentTypeAndRecordEvent() {
        DocumentType documentType = DocumentType.register("CC", "Cédula de Ciudadanía");

        assertNotNull(documentType.id());
        assertEquals("CC", documentType.code());
        assertEquals("Cédula de Ciudadanía", documentType.name());
        assertTrue(documentType.active());
        assertEquals(documentType.createdAt(), documentType.updatedAt());

        assertEquals(1, documentType.domainEvents().size());
        DocumentTypeRegisteredEvent event = assertInstanceOf(
                DocumentTypeRegisteredEvent.class, documentType.domainEvents().getFirst());
        assertEquals(documentType.id(), event.id());
    }

    @Test
    void shouldTrimCodeAndName() {
        DocumentType documentType = DocumentType.register("  CC  ", "  Cédula de Ciudadanía  ");

        assertEquals("CC", documentType.code());
        assertEquals("Cédula de Ciudadanía", documentType.name());
    }

    @Test
    void shouldRejectBlankCode() {
        assertThrows(InvalidDocumentTypeException.class, () -> DocumentType.register("  ", "Nombre"));
    }

    @Test
    void shouldRejectNullCode() {
        assertThrows(InvalidDocumentTypeException.class, () -> DocumentType.register(null, "Nombre"));
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(InvalidDocumentTypeException.class, () -> DocumentType.register("CC", "  "));
    }

    @Test
    void shouldRejectTooLongCode() {
        assertThrows(InvalidDocumentTypeException.class,
                () -> DocumentType.register("x".repeat(DocumentType.CODE_MAX_LENGTH + 1), "Nombre"));
    }

    @Test
    void shouldRejectTooLongName() {
        assertThrows(InvalidDocumentTypeException.class,
                () -> DocumentType.register("CC", "x".repeat(DocumentType.NAME_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        DocumentType documentType = DocumentType.register("CC", "Cédula");
        documentType.clearDomainEvents();

        documentType.update("TI", "Tarjeta de Identidad", false);

        assertEquals("TI", documentType.code());
        assertEquals("Tarjeta de Identidad", documentType.name());
        assertFalse(documentType.active());
        assertFalse(documentType.updatedAt().isBefore(documentType.createdAt()));

        assertEquals(1, documentType.domainEvents().size());
        DocumentTypeUpdatedEvent event = assertInstanceOf(
                DocumentTypeUpdatedEvent.class, documentType.domainEvents().getFirst());
        assertEquals("TI", event.code());
        assertEquals("Tarjeta de Identidad", event.name());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        DocumentType documentType = DocumentType.register("CC", "Cédula");
        documentType.clearDomainEvents();

        assertThrows(InvalidDocumentTypeException.class, () -> documentType.update("", "Nombre", true));
        assertTrue(documentType.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        DocumentType documentType = DocumentType.register("CC", "Cédula");
        documentType.clearDomainEvents();

        documentType.delete();

        assertEquals(1, documentType.domainEvents().size());
        DocumentTypeDeletedEvent event = assertInstanceOf(
                DocumentTypeDeletedEvent.class, documentType.domainEvents().getLast());
        assertEquals(documentType.id(), event.id());
    }
}