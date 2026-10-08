package com.mindconnect.domain.contact.relationshiptype.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.contact.relationshiptype.event.RelationshipTypeDeletedEvent;
import com.mindconnect.domain.contact.relationshiptype.event.RelationshipTypeRegisteredEvent;
import com.mindconnect.domain.contact.relationshiptype.event.RelationshipTypeUpdatedEvent;
import com.mindconnect.domain.contact.relationshiptype.exception.InvalidRelationshipTypeException;

class RelationshipTypeTest {

    @Test
    void shouldRegisterRelationshipTypeAndRecordEvent() {
        RelationshipType relationshipType = RelationshipType.register("Father");

        assertNotNull(relationshipType.id());
        assertEquals("Father", relationshipType.description());

        assertEquals(1, relationshipType.domainEvents().size());
        RelationshipTypeRegisteredEvent event = assertInstanceOf(
                RelationshipTypeRegisteredEvent.class, relationshipType.domainEvents().getFirst());
        assertEquals(relationshipType.id(), event.id());
    }

    @Test
    void shouldTrimDescription() {
        RelationshipType relationshipType = RelationshipType.register("  Mother  ");

        assertEquals("Mother", relationshipType.description());
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThrows(InvalidRelationshipTypeException.class, () -> RelationshipType.register("  "));
    }

    @Test
    void shouldRejectNullDescription() {
        assertThrows(InvalidRelationshipTypeException.class, () -> RelationshipType.register(null));
    }

    @Test
    void shouldRejectTooLongDescription() {
        assertThrows(InvalidRelationshipTypeException.class,
                () -> RelationshipType.register("x".repeat(RelationshipType.DESCRIPTION_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateDescriptionAndRecordEvent() {
        RelationshipType relationshipType = RelationshipType.register("Father");
        relationshipType.clearDomainEvents();

        relationshipType.update("Mother");

        assertEquals("Mother", relationshipType.description());

        assertEquals(1, relationshipType.domainEvents().size());
        RelationshipTypeUpdatedEvent event = assertInstanceOf(
                RelationshipTypeUpdatedEvent.class, relationshipType.domainEvents().getFirst());
        assertEquals("Mother", event.description());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        RelationshipType relationshipType = RelationshipType.register("Father");
        relationshipType.clearDomainEvents();

        assertThrows(InvalidRelationshipTypeException.class, () -> relationshipType.update(""));
        assertTrue(relationshipType.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        RelationshipType relationshipType = RelationshipType.register("Father");
        relationshipType.clearDomainEvents();

        relationshipType.delete();

        assertEquals(1, relationshipType.domainEvents().size());
        RelationshipTypeDeletedEvent event = assertInstanceOf(
                RelationshipTypeDeletedEvent.class, relationshipType.domainEvents().getLast());
        assertEquals(relationshipType.id(), event.id());
    }
}