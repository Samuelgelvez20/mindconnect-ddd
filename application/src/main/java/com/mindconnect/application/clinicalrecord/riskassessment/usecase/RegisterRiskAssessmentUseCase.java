package com.mindconnect.application.clinicalrecord.riskassessment.usecase;

import com.mindconnect.application.clinicalrecord.riskassessment.command.RegisterRiskAssessmentCommand;
import com.mindconnect.application.clinicalrecord.riskassessment.dto.RiskAssessmentResponse;
import com.mindconnect.application.clinicalrecord.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment;
import com.mindconnect.domain.clinicalrecord.riskassessment.port.repository.RiskAssessmentRepository;

public class RegisterRiskAssessmentUseCase {

    private final RiskAssessmentRepository repository;

    public RegisterRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    public RiskAssessmentResponse execute(RegisterRiskAssessmentCommand command) {

        RiskAssessment assessment = RiskAssessment.register(
                command.encounterId(),
                command.riskLevelId(),
                command.suicidalIdeation(),
                command.suicidePlan(),
                command.suicideIntent(),
                command.selfHarm(),
                command.harmToOthers(),
                command.protectiveFactors(),
                command.riskFactors(),
                command.clinicalActions(),
                command.observations(),
                command.assessedBy());

        return RiskAssessmentResponse.from(repository.save(assessment));
    }
}