package com.mindconnect.application.clinicalrecord.riskassessment.usecase;

import com.mindconnect.application.clinicalrecord.riskassessment.dto.RiskAssessmentResponse;
import com.mindconnect.application.clinicalrecord.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.domain.clinicalrecord.riskassessment.port.repository.RiskAssessmentRepository;

public class GetRiskAssessmentByIdUseCase {

    private final RiskAssessmentRepository repository;

    public GetRiskAssessmentByIdUseCase(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    public RiskAssessmentResponse execute(RiskAssessmentId id) {
        return repository.findById(id)
                .map(RiskAssessmentResponse::from)
                .orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(id));
    }
}