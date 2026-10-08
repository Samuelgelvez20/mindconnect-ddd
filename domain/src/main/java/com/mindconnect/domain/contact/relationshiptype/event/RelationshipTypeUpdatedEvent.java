package com.mindconnect.domain.contact.relationshiptype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

public record RelationshipTypeUpdatedEvent(
        RelationshipTypeId id,
        String description,
        Instant occurredOn
) implements DomainEvent {

    public RelationshipTypeUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}