package com.mindconnect.application.clinicalrecord.riskassessment.usecase;

import com.mindconnect.application.clinicalrecord.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.domain.clinicalrecord.riskassessment.port.repository.RiskAssessmentRepository;

public class DeleteRiskAssessmentUseCase {

    private final RiskAssessmentRepository repository;

    public DeleteRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    public void execute(RiskAssessmentId id) {

        var assessment = repository.findById(id)
                .orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(id));

        assessment.delete();
        repository.delete(assessment);
    }
}