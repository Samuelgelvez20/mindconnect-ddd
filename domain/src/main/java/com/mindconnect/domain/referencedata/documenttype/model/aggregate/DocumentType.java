package com.mindconnect.domain.referencedata.documenttype.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.referencedata.documenttype.event.DocumentTypeDeletedEvent;
import com.mindconnect.domain.referencedata.documenttype.event.DocumentTypeRegisteredEvent;
import com.mindconnect.domain.referencedata.documenttype.event.DocumentTypeUpdatedEvent;
import com.mindconnect.domain.referencedata.documenttype.exception.InvalidDocumentTypeException;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class DocumentType extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final DocumentTypeId id;
    private String code;
    private String name;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private DocumentType(
            DocumentTypeId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.code = code;
        this.name = name;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static DocumentType register(
            String code,
            String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        DocumentTypeId id = DocumentTypeId.generate();

        DocumentType documentType = new DocumentType(
                id,
                requiredText(code, "code", CODE_MAX_LENGTH),
                requiredText(name, "name", NAME_MAX_LENGTH),
                true,
                now,
                now);

        documentType.recordEvent(new DocumentTypeRegisteredEvent(id, now));
        return documentType;
    }

    public static DocumentType restore(
            DocumentTypeId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new DocumentType(id, code, name, active, createdAt, updatedAt);
    }

    public void update(
            String code,
            String name,
            boolean active) {

        this.code = requiredText(code, "code", CODE_MAX_LENGTH);
        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new DocumentTypeUpdatedEvent(this.id, this.code, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new DocumentTypeDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public DocumentTypeId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidDocumentTypeException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidDocumentTypeException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}