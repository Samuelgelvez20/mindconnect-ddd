package com.mindconnect.domain.treatment.treatmentgoal.model.aggregate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.treatment.treatmentgoal.event.TreatmentGoalDeletedEvent;
import com.mindconnect.domain.treatment.treatmentgoal.event.TreatmentGoalRegisteredEvent;
import com.mindconnect.domain.treatment.treatmentgoal.event.TreatmentGoalUpdatedEvent;
import com.mindconnect.domain.treatment.treatmentgoal.exception.InvalidTreatmentGoalException;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;

public class TreatmentGoal extends AggregateRoot {

    private final TreatmentGoalId id;
    private TreatmentPlanId treatmentPlanId;
    private String description;
    private LocalDate targetDate;
    private Instant completedAt;
    private TreatmentGoalStatusId treatmentGoalStatusId;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private TreatmentGoal(
            TreatmentGoalId id,
            TreatmentPlanId treatmentPlanId,
            String description,
            LocalDate targetDate,
            Instant completedAt,
            TreatmentGoalStatusId treatmentGoalStatusId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.treatmentPlanId = treatmentPlanId;
        this.description = description;
        this.targetDate = targetDate;
        this.completedAt = completedAt;
        this.treatmentGoalStatusId = treatmentGoalStatusId;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static TreatmentGoal register(
            TreatmentPlanId treatmentPlanId,
            String description,
            LocalDate targetDate,
            TreatmentGoalStatusId treatmentGoalStatusId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        TreatmentGoalId id = TreatmentGoalId.generate();

        String requiredDescription = requiredText(description, "description", Integer.MAX_VALUE);

        TreatmentGoal goal = new TreatmentGoal(
                id,
                treatmentPlanId,
                requiredDescription,
                targetDate,
                null,
                treatmentGoalStatusId,
                true,
                now,
                now);

        goal.recordEvent(new TreatmentGoalRegisteredEvent(id, now));
        return goal;
    }

    public static TreatmentGoal restore(
            TreatmentGoalId id,
            TreatmentPlanId treatmentPlanId,
            String description,
            LocalDate targetDate,
            Instant completedAt,
            TreatmentGoalStatusId treatmentGoalStatusId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new TreatmentGoal(id, treatmentPlanId, description, targetDate, completedAt, treatmentGoalStatusId, active, createdAt, updatedAt);
    }

    public void update(
            TreatmentPlanId treatmentPlanId,
            String description,
            LocalDate targetDate,
            TreatmentGoalStatusId treatmentGoalStatusId) {

        this.treatmentPlanId = treatmentPlanId;
        this.description = requiredText(description, "description", Integer.MAX_VALUE);
        this.targetDate = targetDate;
        this.treatmentGoalStatusId = treatmentGoalStatusId;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new TreatmentGoalUpdatedEvent(this.id, this.treatmentPlanId, this.description, this.targetDate, this.treatmentGoalStatusId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new TreatmentGoalDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public TreatmentGoalId id() {
        return id;
    }

    public TreatmentPlanId treatmentPlanId() {
        return treatmentPlanId;
    }

    public String description() {
        return description;
    }

    public LocalDate targetDate() {
        return targetDate;
    }

    public Instant completedAt() {
        return completedAt;
    }

    public TreatmentGoalStatusId treatmentGoalStatusId() {
        return treatmentGoalStatusId;
    }

    public boolean isActive() {
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
            throw new InvalidTreatmentGoalException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidTreatmentGoalException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}