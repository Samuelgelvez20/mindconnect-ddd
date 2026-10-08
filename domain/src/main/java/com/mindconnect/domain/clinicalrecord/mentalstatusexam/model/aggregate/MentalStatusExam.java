package com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.event.MentalStatusExamDeletedEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.event.MentalStatusExamRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.event.MentalStatusExamUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.exception.InvalidMentalStatusExamException;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class MentalStatusExam extends AggregateRoot {

    private final MentalStatusExamId id;
    private EncounterId encounterId;
    private String appearance;
    private String behavior;
    private String attitude;
    private String consciousness;
    private String orientation;
    private String attention;
    private String memory;
    private String speech;
    private String mood;
    private String affect;
    private String thoughtProcess;
    private String thoughtContent;
    private String perception;
    private String judgment;
    private String insight;
    private String psychomotorActivity;
    private String observations;
    private ProfessionalId createdBy;
    private final Instant createdAt;
    private Instant updatedAt;

    private MentalStatusExam(
            MentalStatusExamId id,
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.encounterId = encounterId;
        this.appearance = appearance;
        this.behavior = behavior;
        this.attitude = attitude;
        this.consciousness = consciousness;
        this.orientation = orientation;
        this.attention = attention;
        this.memory = memory;
        this.speech = speech;
        this.mood = mood;
        this.affect = affect;
        this.thoughtProcess = thoughtProcess;
        this.thoughtContent = thoughtContent;
        this.perception = perception;
        this.judgment = judgment;
        this.insight = insight;
        this.psychomotorActivity = psychomotorActivity;
        this.observations = observations;
        this.createdBy = createdBy;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static MentalStatusExam register(
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        MentalStatusExamId id = MentalStatusExamId.generate();

        if (encounterId == null) {
            throw new InvalidMentalStatusExamException("encounterId must not be null");
        }
        if (createdBy == null) {
            throw new InvalidMentalStatusExamException("createdBy must not be null");
        }

        MentalStatusExam exam = new MentalStatusExam(
                id,
                encounterId,
                optionalText(appearance, "appearance"),
                optionalText(behavior, "behavior"),
                optionalText(attitude, "attitude"),
                optionalText(consciousness, "consciousness"),
                optionalText(orientation, "orientation"),
                optionalText(attention, "attention"),
                optionalText(memory, "memory"),
                optionalText(speech, "speech"),
                optionalText(mood, "mood"),
                optionalText(affect, "affect"),
                optionalText(thoughtProcess, "thoughtProcess"),
                optionalText(thoughtContent, "thoughtContent"),
                optionalText(perception, "perception"),
                optionalText(judgment, "judgment"),
                optionalText(insight, "insight"),
                optionalText(psychomotorActivity, "psychomotorActivity"),
                optionalText(observations, "observations"),
                createdBy,
                now,
                now);

        exam.recordEvent(new MentalStatusExamRegisteredEvent(id, now));
        return exam;
    }

    public static MentalStatusExam restore(
            MentalStatusExamId id,
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy,
            Instant createdAt,
            Instant updatedAt) {

        return new MentalStatusExam(id, encounterId, appearance, behavior, attitude, consciousness,
                orientation, attention, memory, speech, mood, affect, thoughtProcess, thoughtContent,
                perception, judgment, insight, psychomotorActivity, observations, createdBy, createdAt, updatedAt);
    }

    public void update(
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy) {

        this.encounterId = Objects.requireNonNull(encounterId, "encounterId must not be null");
        this.appearance = optionalText(appearance, "appearance");
        this.behavior = optionalText(behavior, "behavior");
        this.attitude = optionalText(attitude, "attitude");
        this.consciousness = optionalText(consciousness, "consciousness");
        this.orientation = optionalText(orientation, "orientation");
        this.attention = optionalText(attention, "attention");
        this.memory = optionalText(memory, "memory");
        this.speech = optionalText(speech, "speech");
        this.mood = optionalText(mood, "mood");
        this.affect = optionalText(affect, "affect");
        this.thoughtProcess = optionalText(thoughtProcess, "thoughtProcess");
        this.thoughtContent = optionalText(thoughtContent, "thoughtContent");
        this.perception = optionalText(perception, "perception");
        this.judgment = optionalText(judgment, "judgment");
        this.insight = optionalText(insight, "insight");
        this.psychomotorActivity = optionalText(psychomotorActivity, "psychomotorActivity");
        this.observations = optionalText(observations, "observations");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new MentalStatusExamUpdatedEvent(this.id, this.encounterId, this.createdBy, this.updatedAt));
    }

    public void delete() {
        recordEvent(new MentalStatusExamDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public MentalStatusExamId id() {
        return id;
    }

    public EncounterId encounterId() {
        return encounterId;
    }

    public String appearance() {
        return appearance;
    }

    public String behavior() {
        return behavior;
    }

    public String attitude() {
        return attitude;
    }

    public String consciousness() {
        return consciousness;
    }

    public String orientation() {
        return orientation;
    }

    public String attention() {
        return attention;
    }

    public String memory() {
        return memory;
    }

    public String speech() {
        return speech;
    }

    public String mood() {
        return mood;
    }

    public String affect() {
        return affect;
    }

    public String thoughtProcess() {
        return thoughtProcess;
    }

    public String thoughtContent() {
        return thoughtContent;
    }

    public String perception() {
        return perception;
    }

    public String judgment() {
        return judgment;
    }

    public String insight() {
        return insight;
    }

    public String psychomotorActivity() {
        return psychomotorActivity;
    }

    public String observations() {
        return observations;
    }

    public ProfessionalId createdBy() {
        return createdBy;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String optionalText(String value, String field) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}