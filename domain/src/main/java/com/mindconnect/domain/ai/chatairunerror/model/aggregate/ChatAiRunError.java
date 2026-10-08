package com.mindconnect.domain.ai.chatairunerror.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.ai.chatairunerror.event.ChatAiRunErrorDeletedEvent;
import com.mindconnect.domain.ai.chatairunerror.event.ChatAiRunErrorRegisteredEvent;
import com.mindconnect.domain.ai.chatairunerror.event.ChatAiRunErrorUpdatedEvent;
import com.mindconnect.domain.ai.chatairunerror.exception.InvalidChatAiRunErrorException;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

public class ChatAiRunError extends AggregateRoot {

    public static final int ERROR_CODE_MAX_LENGTH = 80;
    public static final int PROVIDER_ERROR_ID_MAX_LENGTH = 120;

    private final ChatAiRunErrorId id;
    private ChatAiRunId aiRunId;
    private String errorMessage;
    private String errorCode;
    private String providerErrorId;
    private final Instant createdAt;

    private ChatAiRunError(
            ChatAiRunErrorId id,
            ChatAiRunId aiRunId,
            String errorMessage,
            String errorCode,
            String providerErrorId,
            Instant createdAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.aiRunId = Objects.requireNonNull(aiRunId, "aiRunId must not be null");
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.providerErrorId = providerErrorId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static ChatAiRunError register(
            ChatAiRunId aiRunId,
            String errorMessage,
            String errorCode,
            String providerErrorId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatAiRunErrorId id = ChatAiRunErrorId.generate();

        if (aiRunId == null) {
            throw new InvalidChatAiRunErrorException("aiRunId must not be null");
        }

        ChatAiRunError error = new ChatAiRunError(
                id,
                aiRunId,
                requiredText(errorMessage, "errorMessage"),
                optionalText(errorCode, "errorCode", ERROR_CODE_MAX_LENGTH),
                optionalText(providerErrorId, "providerErrorId", PROVIDER_ERROR_ID_MAX_LENGTH),
                now);

        error.recordEvent(new ChatAiRunErrorRegisteredEvent(id, now));
        return error;
    }

    public static ChatAiRunError restore(
            ChatAiRunErrorId id,
            ChatAiRunId aiRunId,
            String errorMessage,
            String errorCode,
            String providerErrorId,
            Instant createdAt) {

        return new ChatAiRunError(id, aiRunId, errorMessage, errorCode, providerErrorId, createdAt);
    }

    public void update(
            ChatAiRunId aiRunId,
            String errorMessage,
            String errorCode,
            String providerErrorId) {

        if (aiRunId == null) {
            throw new InvalidChatAiRunErrorException("aiRunId must not be null");
        }

        this.aiRunId = aiRunId;
        this.errorMessage = requiredText(errorMessage, "errorMessage");
        this.errorCode = optionalText(errorCode, "errorCode", ERROR_CODE_MAX_LENGTH);
        this.providerErrorId = optionalText(providerErrorId, "providerErrorId", PROVIDER_ERROR_ID_MAX_LENGTH);

        recordEvent(new ChatAiRunErrorUpdatedEvent(this.id, this.aiRunId, this.errorMessage, this.errorCode, this.providerErrorId, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public void delete() {
        recordEvent(new ChatAiRunErrorDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatAiRunErrorId id() {
        return id;
    }

    public ChatAiRunId aiRunId() {
        return aiRunId;
    }

    public String errorMessage() {
        return errorMessage;
    }

    public String errorCode() {
        return errorCode;
    }

    public String providerErrorId() {
        return providerErrorId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    private static String requiredText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidChatAiRunErrorException(field + " must not be blank");
        }
        return value.trim();
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidChatAiRunErrorException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}