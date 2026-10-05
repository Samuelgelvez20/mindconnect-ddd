package com.mindconnect.application.clinicalrecord.riskassessment.usecase;

import java.util.List;

import com.mindconnect.application.clinicalrecord.riskassessment.dto.RiskAssessmentResponse;
import com.mindconnect.domain.clinicalrecord.riskassessment.port.repository.RiskAssessmentRepository;

public class ListRiskAssessmentUseCase {

    private final RiskAssessmentRepository repository;

    public ListRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    public List<RiskAssessmentResponse> execute() {
        return repository.findAll()
                .stream()
                .map(RiskAssessmentResponse::from)
                .toList();
    }
}