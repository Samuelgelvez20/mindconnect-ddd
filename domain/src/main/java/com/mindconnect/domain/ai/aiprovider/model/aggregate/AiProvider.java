package com.mindconnect.domain.ai.aiprovider.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.ai.aiprovider.event.AiProviderDeletedEvent;
import com.mindconnect.domain.ai.aiprovider.event.AiProviderRegisteredEvent;
import com.mindconnect.domain.ai.aiprovider.event.AiProviderUpdatedEvent;
import com.mindconnect.domain.ai.aiprovider.exception.InvalidAiProviderException;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

public class AiProvider extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 100;
    public static final int LEGAL_NAME_MAX_LENGTH = 255;

    private final AiProviderId id;
    private String name;
    private String legalName;
    private String website;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private AiProvider(
            AiProviderId id,
            String name,
            String legalName,
            String website,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.legalName = legalName;
        this.website = website;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static AiProvider register(
            String name,
            String legalName,
            String website) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        AiProviderId id = AiProviderId.generate();

        AiProvider provider = new AiProvider(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                optionalText(legalName, "legalName", LEGAL_NAME_MAX_LENGTH),
                optionalText(website, "website", -1),
                true,
                now,
                now);

        provider.recordEvent(new AiProviderRegisteredEvent(id, now));
        return provider;
    }

    public static AiProvider restore(
            AiProviderId id,
            String name,
            String legalName,
            String website,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new AiProvider(id, name, legalName, website, active, createdAt, updatedAt);
    }

    public void update(
            String name,
            String legalName,
            String website,
            boolean active) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.legalName = optionalText(legalName, "legalName", LEGAL_NAME_MAX_LENGTH);
        this.website = optionalText(website, "website", -1);
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new AiProviderUpdatedEvent(this.id, this.name, this.legalName, this.website, this.active, this.updatedAt));
    }

    public void delete() {
        recordEvent(new AiProviderDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public AiProviderId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String legalName() {
        return legalName;
    }

    public String website() {
        return website;
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
            throw new InvalidAiProviderException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (maxLength > 0 && trimmed.length() > maxLength) {
            throw new InvalidAiProviderException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (maxLength > 0 && trimmed.length() > maxLength) {
            throw new InvalidAiProviderException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}