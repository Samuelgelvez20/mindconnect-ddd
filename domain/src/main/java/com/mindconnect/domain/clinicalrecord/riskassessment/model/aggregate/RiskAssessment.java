package com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.riskassessment.event.RiskAssessmentDeletedEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.event.RiskAssessmentRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.event.RiskAssessmentUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.exception.InvalidRiskAssessmentException;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class RiskAssessment extends AggregateRoot {

    private final RiskAssessmentId id;
    private EncounterId encounterId;
    private RiskLevelId riskLevelId;
    private boolean suicidalIdeation;
    private boolean suicidePlan;
    private boolean suicideIntent;
    private boolean selfHarm;
    private boolean harmToOthers;
    private String protectiveFactors;
    private String riskFactors;
    private String clinicalActions;
    private String observations;
    private Instant assessedAt;
    private ProfessionalId assessedBy;
    private final Instant createdAt;
    private Instant updatedAt;

    private RiskAssessment(
            RiskAssessmentId id,
            EncounterId encounterId,
            RiskLevelId riskLevelId,
            boolean suicidalIdeation,
            boolean suicidePlan,
            boolean suicideIntent,
            boolean selfHarm,
            boolean harmToOthers,
            String protectiveFactors,
            String riskFactors,
            String clinicalActions,
            String observations,
            Instant assessedAt,
            ProfessionalId assessedBy,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.encounterId = encounterId;
        this.riskLevelId = riskLevelId;
        this.suicidalIdeation = suicidalIdeation;
        this.suicidePlan = suicidePlan;
        this.suicideIntent = suicideIntent;
        this.selfHarm = selfHarm;
        this.harmToOthers = harmToOthers;
        this.protectiveFactors = protectiveFactors;
        this.riskFactors = riskFactors;
        this.clinicalActions = clinicalActions;
        this.observations = observations;
        this.assessedAt = assessedAt;
        this.assessedBy = assessedBy;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static RiskAssessment register(
            EncounterId encounterId,
            RiskLevelId riskLevelId,
            boolean suicidalIdeation,
            boolean suicidePlan,
            boolean suicideIntent,
            boolean selfHarm,
            boolean harmToOthers,
            String protectiveFactors,
            String riskFactors,
            String clinicalActions,
            String observations,
            ProfessionalId assessedBy) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        RiskAssessmentId id = RiskAssessmentId.generate();

        if (encounterId == null) {
            throw new InvalidRiskAssessmentException("encounterId must not be null");
        }
        if (riskLevelId == null) {
            throw new InvalidRiskAssessmentException("riskLevelId must not be null");
        }
        if (assessedBy == null) {
            throw new InvalidRiskAssessmentException("assessedBy must not be null");
        }

        RiskAssessment assessment = new RiskAssessment(
                id,
                encounterId,
                riskLevelId,
                suicidalIdeation,
                suicidePlan,
                suicideIntent,
                selfHarm,
                harmToOthers,
                optionalText(protectiveFactors, "protectiveFactors"),
                optionalText(riskFactors, "riskFactors"),
                optionalText(clinicalActions, "clinicalActions"),
                optionalText(observations, "observations"),
                now,
                assessedBy,
                now,
                now);

        assessment.recordEvent(new RiskAssessmentRegisteredEvent(id, now));
        return assessment;
    }

    public static RiskAssessment restore(
            RiskAssessmentId id,
            EncounterId encounterId,
            RiskLevelId riskLevelId,
            boolean suicidalIdeation,
            boolean suicidePlan,
            boolean suicideIntent,
            boolean selfHarm,
            boolean harmToOthers,
            String protectiveFactors,
            String riskFactors,
            String clinicalActions,
            String observations,
            Instant assessedAt,
            ProfessionalId assessedBy,
            Instant createdAt,
            Instant updatedAt) {

        return new RiskAssessment(id, encounterId, riskLevelId, suicidalIdeation, suicidePlan,
                suicideIntent, selfHarm, harmToOthers, protectiveFactors, riskFactors,
                clinicalActions, observations, assessedAt, assessedBy, createdAt, updatedAt);
    }

    public void update(
            EncounterId encounterId,
            RiskLevelId riskLevelId,
            boolean suicidalIdeation,
            boolean suicidePlan,
            boolean suicideIntent,
            boolean selfHarm,
            boolean harmToOthers,
            String protectiveFactors,
            String riskFactors,
            String clinicalActions,
            String observations,
            Instant assessedAt,
            ProfessionalId assessedBy) {

        this.encounterId = Objects.requireNonNull(encounterId, "encounterId must not be null");
        this.riskLevelId = Objects.requireNonNull(riskLevelId, "riskLevelId must not be null");
        this.suicidalIdeation = suicidalIdeation;
        this.suicidePlan = suicidePlan;
        this.suicideIntent = suicideIntent;
        this.selfHarm = selfHarm;
        this.harmToOthers = harmToOthers;
        this.protectiveFactors = optionalText(protectiveFactors, "protectiveFactors");
        this.riskFactors = optionalText(riskFactors, "riskFactors");
        this.clinicalActions = optionalText(clinicalActions, "clinicalActions");
        this.observations = optionalText(observations, "observations");
        this.assessedAt = assessedAt != null ? assessedAt : Instant.now().truncatedTo(ChronoUnit.MICROS);
        this.assessedBy = Objects.requireNonNull(assessedBy, "assessedBy must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new RiskAssessmentUpdatedEvent(
                this.id, this.encounterId, this.riskLevelId,
                this.suicidalIdeation, this.suicidePlan, this.suicideIntent,
                this.selfHarm, this.harmToOthers, this.assessedBy, this.updatedAt));
    }

    public void delete() {
        recordEvent(new RiskAssessmentDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public RiskAssessmentId id() {
        return id;
    }

    public EncounterId encounterId() {
        return encounterId;
    }

    public RiskLevelId riskLevelId() {
        return riskLevelId;
    }

    public boolean suicidalIdeation() {
        return suicidalIdeation;
    }

    public boolean suicidePlan() {
        return suicidePlan;
    }

    public boolean suicideIntent() {
        return suicideIntent;
    }

    public boolean selfHarm() {
        return selfHarm;
    }

    public boolean harmToOthers() {
        return harmToOthers;
    }

    public String protectiveFactors() {
        return protectiveFactors;
    }

    public String riskFactors() {
        return riskFactors;
    }

    public String clinicalActions() {
        return clinicalActions;
    }

    public String observations() {
        return observations;
    }

    public Instant assessedAt() {
        return assessedAt;
    }

    public ProfessionalId assessedBy() {
        return assessedBy;
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