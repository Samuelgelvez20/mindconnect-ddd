package com.mindconnect.domain.treatment.treatmentplan.model.aggregate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.treatment.treatmentplan.event.TreatmentPlanDeletedEvent;
import com.mindconnect.domain.treatment.treatmentplan.event.TreatmentPlanRegisteredEvent;
import com.mindconnect.domain.treatment.treatmentplan.event.TreatmentPlanUpdatedEvent;
import com.mindconnect.domain.treatment.treatmentplan.exception.InvalidTreatmentPlanException;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;

public class TreatmentPlan extends AggregateRoot {

    private final TreatmentPlanId id;
    private EncounterId encounterId;
    private ProfessionalId professionalId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TreatmentStatusId treatmentStatusId;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private TreatmentPlan(
            TreatmentPlanId id,
            EncounterId encounterId,
            ProfessionalId professionalId,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            TreatmentStatusId treatmentStatusId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.encounterId = encounterId;
        this.professionalId = professionalId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.treatmentStatusId = treatmentStatusId;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static TreatmentPlan register(
            EncounterId encounterId,
            ProfessionalId professionalId,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            TreatmentStatusId treatmentStatusId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        TreatmentPlanId id = TreatmentPlanId.generate();

        String requiredTitle = requiredText(title, "title", 200);

        TreatmentPlan plan = new TreatmentPlan(
                id,
                encounterId,
                professionalId,
                requiredTitle,
                description,
                startDate,
                endDate,
                treatmentStatusId,
                true,
                now,
                now);

        plan.recordEvent(new TreatmentPlanRegisteredEvent(id, now));
        return plan;
    }

    public static TreatmentPlan restore(
            TreatmentPlanId id,
            EncounterId encounterId,
            ProfessionalId professionalId,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            TreatmentStatusId treatmentStatusId,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new TreatmentPlan(id, encounterId, professionalId, title, description, startDate, endDate, treatmentStatusId, active, createdAt, updatedAt);
    }

    public void update(
            EncounterId encounterId,
            ProfessionalId professionalId,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            TreatmentStatusId treatmentStatusId) {

        this.encounterId = encounterId;
        this.professionalId = professionalId;
        this.title = requiredText(title, "title", 200);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.treatmentStatusId = treatmentStatusId;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new TreatmentPlanUpdatedEvent(this.id, this.encounterId, this.professionalId, this.title, this.description, this.startDate, this.endDate, this.treatmentStatusId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new TreatmentPlanDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public TreatmentPlanId id() {
        return id;
    }

    public EncounterId encounterId() {
        return encounterId;
    }

    public ProfessionalId professionalId() {
        return professionalId;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public TreatmentStatusId treatmentStatusId() {
        return treatmentStatusId;
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

    public void setActive(boolean active) {
        this.active = active;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidTreatmentPlanException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidTreatmentPlanException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}