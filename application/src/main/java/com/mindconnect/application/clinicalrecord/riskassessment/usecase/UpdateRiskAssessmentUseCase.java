package com.mindconnect.application.clinicalrecord.riskassessment.usecase;

import com.mindconnect.application.clinicalrecord.riskassessment.command.UpdateRiskAssessmentCommand;
import com.mindconnect.application.clinicalrecord.riskassessment.dto.RiskAssessmentResponse;
import com.mindconnect.application.clinicalrecord.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment;
import com.mindconnect.domain.clinicalrecord.riskassessment.port.repository.RiskAssessmentRepository;

public class UpdateRiskAssessmentUseCase {

    private final RiskAssessmentRepository repository;

    public UpdateRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    public RiskAssessmentResponse execute(UpdateRiskAssessmentCommand command) {

        var assessment = repository.findById(command.id())
                .orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(command.id()));

        assessment.update(
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
                command.assessedAt(),
                command.assessedBy());

        return RiskAssessmentResponse.from(repository.save(assessment));
    }
}