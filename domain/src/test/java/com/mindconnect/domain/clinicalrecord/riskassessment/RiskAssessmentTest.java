package com.mindconnect.domain.clinicalrecord.riskassessment;

import java.time.Instant;
import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.riskassessment.event.RiskAssessmentDeletedEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.event.RiskAssessmentRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.event.RiskAssessmentUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.exception.InvalidRiskAssessmentException;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RiskAssessmentTest {

    @Test
    void register_ValidData_CreatesAssessmentWithEvents() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId,
                true, false, false, false, false,
                "Strong family support", "History of depression",
                "Outpatient follow-up", "Patient cooperative",
                assessedBy);

        assertNotNull(assessment.id());
        assertEquals(encounterId, assessment.encounterId());
        assertEquals(riskLevelId, assessment.riskLevelId());
        assertTrue(assessment.suicidalIdeation());
        assertFalse(assessment.suicidePlan());
        assertFalse(assessment.suicideIntent());
        assertFalse(assessment.selfHarm());
        assertFalse(assessment.harmToOthers());
        assertEquals("Strong family support", assessment.protectiveFactors());
        assertEquals("History of depression", assessment.riskFactors());
        assertEquals("Outpatient follow-up", assessment.clinicalActions());
        assertEquals("Patient cooperative", assessment.observations());
        assertNotNull(assessment.assessedAt());
        assertEquals(assessedBy, assessment.assessedBy());
        assertNotNull(assessment.createdAt());
        assertNotNull(assessment.updatedAt());

        var events = assessment.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof RiskAssessmentRegisteredEvent);
    }

    @Test
    void register_WithNullOptionalFields_CreatesAssessmentWithNulls() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId,
                false, false, false, false, false,
                null, null, null, null, assessedBy);

        assertNull(assessment.protectiveFactors());
        assertNull(assessment.riskFactors());
        assertNull(assessment.clinicalActions());
        assertNull(assessment.observations());
    }

    @Test
    void register_NullEncounterId_ThrowsException() {
        assertThrows(InvalidRiskAssessmentException.class,
                () -> RiskAssessment.register(null, RiskLevelId.generate(),
                        false, false, false, false, false, null, null, null, null, ProfessionalId.generate()));
    }

    @Test
    void register_NullRiskLevelId_ThrowsException() {
        assertThrows(InvalidRiskAssessmentException.class,
                () -> RiskAssessment.register(EncounterId.generate(), null,
                        false, false, false, false, false, null, null, null, null, ProfessionalId.generate()));
    }

    @Test
    void register_NullAssessedBy_ThrowsException() {
        assertThrows(InvalidRiskAssessmentException.class,
                () -> RiskAssessment.register(EncounterId.generate(), RiskLevelId.generate(),
                        false, false, false, false, false, null, null, null, null, null));
    }

    @Test
    void restore_CreatesAssessmentWithoutEvents() {
        RiskAssessmentId id = RiskAssessmentId.generate();
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        Instant assessedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        RiskAssessment assessment = RiskAssessment.restore(
                id, encounterId, riskLevelId,
                true, false, false, false, false,
                "protective", "risk", "actions", "obs",
                assessedAt, ProfessionalId.generate(), now, now);

        assertEquals(id, assessment.id());
        assertEquals(encounterId, assessment.encounterId());
        assertEquals(riskLevelId, assessment.riskLevelId());
        assertTrue(assessment.suicidalIdeation());
        assertEquals("protective", assessment.protectiveFactors());
        assertEquals("risk", assessment.riskFactors());
        assertEquals("actions", assessment.clinicalActions());
        assertEquals("obs", assessment.observations());
        assertEquals(assessedAt, assessment.assessedAt());
        assertEquals(now, assessment.createdAt());
        assertEquals(now, assessment.updatedAt());
        assertTrue(assessment.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId,
                false, false, false, false, false,
                "old protective", "old risk", "old actions", "old obs", assessedBy);
        int initialEventCount = assessment.domainEvents().size();

        EncounterId newEncounterId = EncounterId.generate();
        RiskLevelId newRiskLevelId = RiskLevelId.generate();
        ProfessionalId newAssessedBy = ProfessionalId.generate();
        Instant newAssessedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        assessment.update(
                newEncounterId, newRiskLevelId,
                true, true, false, false, false,
                "new protective", "new risk", "new actions", "new obs",
                newAssessedAt, newAssessedBy);

        assertEquals(newEncounterId, assessment.encounterId());
        assertEquals(newRiskLevelId, assessment.riskLevelId());
        assertTrue(assessment.suicidalIdeation());
        assertTrue(assessment.suicidePlan());
        assertFalse(assessment.suicideIntent());
        assertFalse(assessment.selfHarm());
        assertFalse(assessment.harmToOthers());
        assertEquals("new protective", assessment.protectiveFactors());
        assertEquals("new risk", assessment.riskFactors());
        assertEquals("new actions", assessment.clinicalActions());
        assertEquals("new obs", assessment.observations());
        assertEquals(newAssessedAt, assessment.assessedAt());
        assertEquals(newAssessedBy, assessment.assessedBy());
        assertNotNull(assessment.updatedAt());

        var events = assessment.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof RiskAssessmentUpdatedEvent);
    }

    @Test
    void update_WithNullOptionalFields_UpdatesToNull() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId,
                false, false, false, false, false,
                "old protective", "old risk", "old actions", "old obs", assessedBy);

        assessment.update(
                EncounterId.generate(), RiskLevelId.generate(),
                false, false, false, false, false,
                null, null, null, null,
                Instant.now(), ProfessionalId.generate());

        assertNull(assessment.protectiveFactors());
        assertNull(assessment.riskFactors());
        assertNull(assessment.clinicalActions());
        assertNull(assessment.observations());
    }

    @Test
    void update_NullEncounterId_ThrowsException() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId, false, false, false, false, false,
                null, null, null, null, assessedBy);

        assertThrows(InvalidRiskAssessmentException.class,
                () -> assessment.update(null, RiskLevelId.generate(),
                        false, false, false, false, false,
                        null, null, null, null, Instant.now(), ProfessionalId.generate()));
    }

    @Test
    void update_NullRiskLevelId_ThrowsException() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId, false, false, false, false, false,
                null, null, null, null, assessedBy);

        assertThrows(InvalidRiskAssessmentException.class,
                () -> assessment.update(EncounterId.generate(), null,
                        false, false, false, false, false,
                        null, null, null, null, Instant.now(), ProfessionalId.generate()));
    }

    @Test
    void update_NullAssessedBy_ThrowsException() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId, false, false, false, false, false,
                null, null, null, null, assessedBy);

        assertThrows(InvalidRiskAssessmentException.class,
                () -> assessment.update(EncounterId.generate(), RiskLevelId.generate(),
                        false, false, false, false, false,
                        null, null, null, null, Instant.now(), null));
    }

    @Test
    void delete_RegistersDeletedEvent() {
        EncounterId encounterId = EncounterId.generate();
        RiskLevelId riskLevelId = RiskLevelId.generate();
        ProfessionalId assessedBy = ProfessionalId.generate();

        RiskAssessment assessment = RiskAssessment.register(
                encounterId, riskLevelId, false, false, false, false, false,
                null, null, null, null, assessedBy);
        int initialEventCount = assessment.domainEvents().size();

        assessment.delete();

        var events = assessment.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof RiskAssessmentDeletedEvent);
    }
}