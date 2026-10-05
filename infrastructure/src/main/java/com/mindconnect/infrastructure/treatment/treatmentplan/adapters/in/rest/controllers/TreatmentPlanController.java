package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.in.rest.controllers;

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

import com.mindconnect.application.treatment.treatmentplan.command.RegisterTreatmentPlanCommand;
import com.mindconnect.application.treatment.treatmentplan.command.UpdateTreatmentPlanCommand;
import com.mindconnect.application.treatment.treatmentplan.dto.TreatmentPlanResponse;
import com.mindconnect.application.treatment.treatmentplan.usecase.DeleteTreatmentPlanUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.GetTreatmentPlanByIdUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.ListTreatmentPlanUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.RegisterTreatmentPlanUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.UpdateTreatmentPlanUseCase;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.in.rest.dtos.CreateTreatmentPlanRequest;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.in.rest.dtos.UpdateTreatmentPlanRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treatment-plans")
public class TreatmentPlanController {

    private final RegisterTreatmentPlanUseCase registerUseCase;
    private final GetTreatmentPlanByIdUseCase getByIdUseCase;
    private final ListTreatmentPlanUseCase listUseCase;
    private final UpdateTreatmentPlanUseCase updateUseCase;
    private final DeleteTreatmentPlanUseCase deleteUseCase;

    public TreatmentPlanController(
            RegisterTreatmentPlanUseCase registerUseCase,
            GetTreatmentPlanByIdUseCase getByIdUseCase,
            ListTreatmentPlanUseCase listUseCase,
            UpdateTreatmentPlanUseCase updateUseCase,
            DeleteTreatmentPlanUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<TreatmentPlanResponse> create(@Valid @RequestBody CreateTreatmentPlanRequest request) {

        var command = new RegisterTreatmentPlanCommand(
                request.encounterId(),
                request.professionalId(),
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate(),
                request.treatmentStatusId());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentPlanResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentPlanResponse> findById(@PathVariable String id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId(java.util.UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentPlanResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateTreatmentPlanRequest request) {

        var command = new UpdateTreatmentPlanCommand(
                id,
                request.encounterId(),
                request.professionalId(),
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate(),
                request.treatmentStatusId());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}