package com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.in.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindconnect.application.clinicalrecord.riskassessment.command.RegisterRiskAssessmentCommand;
import com.mindconnect.application.clinicalrecord.riskassessment.command.UpdateRiskAssessmentCommand;
import com.mindconnect.application.clinicalrecord.riskassessment.dto.RiskAssessmentResponse;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.DeleteRiskAssessmentUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.GetRiskAssessmentByIdUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.ListRiskAssessmentUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.RegisterRiskAssessmentUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.UpdateRiskAssessmentUseCase;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.in.rest.dtos.CreateRiskAssessmentRequest;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.in.rest.dtos.UpdateRiskAssessmentRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/risk-assessments")
public class RiskAssessmentController {

    private final RegisterRiskAssessmentUseCase registerUseCase;
    private final GetRiskAssessmentByIdUseCase getByIdUseCase;
    private final ListRiskAssessmentUseCase listUseCase;
    private final UpdateRiskAssessmentUseCase updateUseCase;
    private final DeleteRiskAssessmentUseCase deleteUseCase;

    public RiskAssessmentController(
            RegisterRiskAssessmentUseCase registerUseCase,
            GetRiskAssessmentByIdUseCase getByIdUseCase,
            ListRiskAssessmentUseCase listUseCase,
            UpdateRiskAssessmentUseCase updateUseCase,
            DeleteRiskAssessmentUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<RiskAssessmentResponse> create(@Valid @RequestBody CreateRiskAssessmentRequest request) {

        var command = new RegisterRiskAssessmentCommand(
                new com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId(request.encounterId()),
                new com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId(request.riskLevelId()),
                request.suicidalIdeation() != null ? request.suicidalIdeation() : false,
                request.suicidePlan() != null ? request.suicidePlan() : false,
                request.suicideIntent() != null ? request.suicideIntent() : false,
                request.selfHarm() != null ? request.selfHarm() : false,
                request.harmToOthers() != null ? request.harmToOthers() : false,
                request.protectiveFactors(),
                request.riskFactors(),
                request.clinicalActions(),
                request.observations(),
                new com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId(request.assessedBy())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<RiskAssessmentResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskAssessmentResponse> findById(@PathVariable String id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId(java.util.UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskAssessmentResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateRiskAssessmentRequest request) {

        var command = new UpdateRiskAssessmentCommand(
                new com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId(java.util.UUID.fromString(id)),
                new com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId(request.encounterId()),
                new com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId(request.riskLevelId()),
                request.suicidalIdeation() != null ? request.suicidalIdeation() : false,
                request.suicidePlan() != null ? request.suicidePlan() : false,
                request.suicideIntent() != null ? request.suicideIntent() : false,
                request.selfHarm() != null ? request.selfHarm() : false,
                request.harmToOthers() != null ? request.harmToOthers() : false,
                request.protectiveFactors(),
                request.riskFactors(),
                request.clinicalActions(),
                request.observations(),
                request.assessedAt(),
                new com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId(request.assessedBy())
        );

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}