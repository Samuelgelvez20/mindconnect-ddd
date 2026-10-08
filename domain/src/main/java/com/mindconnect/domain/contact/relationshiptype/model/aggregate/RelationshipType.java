package com.mindconnect.domain.contact.relationshiptype.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.contact.relationshiptype.event.RelationshipTypeDeletedEvent;
import com.mindconnect.domain.contact.relationshiptype.event.RelationshipTypeRegisteredEvent;
import com.mindconnect.domain.contact.relationshiptype.event.RelationshipTypeUpdatedEvent;
import com.mindconnect.domain.contact.relationshiptype.exception.InvalidRelationshipTypeException;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

public class RelationshipType extends AggregateRoot {

    public static final int DESCRIPTION_MAX_LENGTH = 50;

    private final RelationshipTypeId id;
    private String description;

    private RelationshipType(
            RelationshipTypeId id,
            String description) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.description = description;
    }

    public static RelationshipType register(String description) {

        RelationshipTypeId id = RelationshipTypeId.generate();

        RelationshipType relationshipType = new RelationshipType(
                id,
                requiredText(description, "description", DESCRIPTION_MAX_LENGTH));

        relationshipType.recordEvent(new RelationshipTypeRegisteredEvent(id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
        return relationshipType;
    }

    public static RelationshipType restore(
            RelationshipTypeId id,
            String description) {

        return new RelationshipType(id, description);
    }

    public void update(String description) {

        this.description = requiredText(description, "description", DESCRIPTION_MAX_LENGTH);

        recordEvent(new RelationshipTypeUpdatedEvent(this.id, this.description, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public void delete() {
        recordEvent(new RelationshipTypeDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public RelationshipTypeId id() {
        return id;
    }

    public String description() {
        return description;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidRelationshipTypeException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidRelationshipTypeException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}