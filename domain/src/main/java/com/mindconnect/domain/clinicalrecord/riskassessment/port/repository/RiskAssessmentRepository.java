package com.mindconnect.domain.clinicalrecord.riskassessment.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;

public interface RiskAssessmentRepository {

    RiskAssessment save(RiskAssessment riskAssessment);

    Optional<RiskAssessment> findById(RiskAssessmentId id);

    List<RiskAssessment> findAll();

    List<RiskAssessment> findByEncounterId(EncounterId encounterId);

    void delete(RiskAssessment riskAssessment);
}