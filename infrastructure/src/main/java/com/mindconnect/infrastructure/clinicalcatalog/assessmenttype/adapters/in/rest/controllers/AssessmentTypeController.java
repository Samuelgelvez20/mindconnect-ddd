package com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.in.rest.controllers;

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

import com.mindconnect.application.clinicalcatalog.assessmenttype.command.RegisterAssessmentTypeCommand;
import com.mindconnect.application.clinicalcatalog.assessmenttype.command.UpdateAssessmentTypeCommand;
import com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.DeleteAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.GetAssessmentTypeByIdUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.ListAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.RegisterAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.UpdateAssessmentTypeUseCase;
import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.in.rest.dtos.CreateAssessmentTypeRequest;
import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.in.rest.dtos.UpdateAssessmentTypeRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assessment-types")
public class AssessmentTypeController {

    private final RegisterAssessmentTypeUseCase registerUseCase;
    private final GetAssessmentTypeByIdUseCase getByIdUseCase;
    private final ListAssessmentTypeUseCase listUseCase;
    private final UpdateAssessmentTypeUseCase updateUseCase;
    private final DeleteAssessmentTypeUseCase deleteUseCase;

    public AssessmentTypeController(
            RegisterAssessmentTypeUseCase registerUseCase,
            GetAssessmentTypeByIdUseCase getByIdUseCase,
            ListAssessmentTypeUseCase listUseCase,
            UpdateAssessmentTypeUseCase updateUseCase,
            DeleteAssessmentTypeUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse> create(@Valid @RequestBody CreateAssessmentTypeRequest request) {

        var command = new RegisterAssessmentTypeCommand(request.code(), request.name(), request.description());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse> findById(@PathVariable String id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId(java.util.UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateAssessmentTypeRequest request) {

        var command = new UpdateAssessmentTypeCommand(request.code(), request.name(), request.description(), request.active());
        var assessmentTypeId = new com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId(java.util.UUID.fromString(id));

        return ResponseEntity.ok(updateUseCase.execute(assessmentTypeId, command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}