package com.mindconnect.domain.chat.chatparticipant.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatparticipant.event.ChatParticipantDeletedEvent;
import com.mindconnect.domain.chat.chatparticipant.event.ChatParticipantRegisteredEvent;
import com.mindconnect.domain.chat.chatparticipant.event.ChatParticipantUpdatedEvent;
import com.mindconnect.domain.chat.chatparticipant.exception.InvalidChatParticipantException;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class ChatParticipant extends AggregateRoot {

    private final ChatParticipantId id;
    private ChatConversationId conversationId;
    private SenderTypeId participantTypeId;
    private PatientId patientId;
    private ProfessionalId professionalId;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatParticipant(
            ChatParticipantId id,
            ChatConversationId conversationId,
            SenderTypeId participantTypeId,
            PatientId patientId,
            ProfessionalId professionalId,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.conversationId = conversationId;
        this.participantTypeId = participantTypeId;
        this.patientId = patientId;
        this.professionalId = professionalId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatParticipant register(
            ChatConversationId conversationId,
            SenderTypeId participantTypeId,
            PatientId patientId,
            ProfessionalId professionalId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatParticipantId id = ChatParticipantId.generate();

        if (conversationId == null) {
            throw new InvalidChatParticipantException("conversationId must not be null");
        }
        if (participantTypeId == null) {
            throw new InvalidChatParticipantException("participantTypeId must not be null");
        }
        if (patientId == null && professionalId == null) {
            throw new InvalidChatParticipantException("patientId or professionalId must not be null");
        }
        if (patientId != null && professionalId != null) {
            throw new InvalidChatParticipantException("patientId and professionalId cannot both be set");
        }

        ChatParticipant participant = new ChatParticipant(
                id,
                conversationId,
                participantTypeId,
                patientId,
                professionalId,
                now,
                now);

        participant.recordEvent(new ChatParticipantRegisteredEvent(id, now));
        return participant;
    }

    public static ChatParticipant restore(
            ChatParticipantId id,
            ChatConversationId conversationId,
            SenderTypeId participantTypeId,
            PatientId patientId,
            ProfessionalId professionalId,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatParticipant(id, conversationId, participantTypeId, patientId, professionalId, createdAt, updatedAt);
    }

    public void update(
            ChatConversationId conversationId,
            SenderTypeId participantTypeId,
            PatientId patientId,
            ProfessionalId professionalId) {

        this.conversationId = Objects.requireNonNull(conversationId, "conversationId must not be null");
        this.participantTypeId = Objects.requireNonNull(participantTypeId, "participantTypeId must not be null");

        if (patientId == null && professionalId == null) {
            throw new InvalidChatParticipantException("patientId or professionalId must not be null");
        }
        if (patientId != null && professionalId != null) {
            throw new InvalidChatParticipantException("patientId and professionalId cannot both be set");
        }

        this.patientId = patientId;
        this.professionalId = professionalId;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatParticipantUpdatedEvent(this.id, this.conversationId, this.participantTypeId, this.patientId, this.professionalId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatParticipantDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatParticipantId id() {
        return id;
    }

    public ChatConversationId conversationId() {
        return conversationId;
    }

    public SenderTypeId participantTypeId() {
        return participantTypeId;
    }

    public PatientId patientId() {
        return patientId;
    }

    public ProfessionalId professionalId() {
        return professionalId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}