package com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.in.rest.controllers;

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

import com.mindconnect.application.treatment.treatmentgoalstatus.command.RegisterTreatmentGoalStatusCommand;
import com.mindconnect.application.treatment.treatmentgoalstatus.command.UpdateTreatmentGoalStatusCommand;
import com.mindconnect.application.treatment.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.DeleteTreatmentGoalStatusUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.GetTreatmentGoalStatusByIdUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.ListTreatmentGoalStatusUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.RegisterTreatmentGoalStatusUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.UpdateTreatmentGoalStatusUseCase;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.in.rest.dtos.CreateTreatmentGoalStatusRequest;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.in.rest.dtos.UpdateTreatmentGoalStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treatment-goal-statuses")
public class TreatmentGoalStatusController {

    private final RegisterTreatmentGoalStatusUseCase registerUseCase;
    private final GetTreatmentGoalStatusByIdUseCase getByIdUseCase;
    private final ListTreatmentGoalStatusUseCase listUseCase;
    private final UpdateTreatmentGoalStatusUseCase updateUseCase;
    private final DeleteTreatmentGoalStatusUseCase deleteUseCase;

    public TreatmentGoalStatusController(
            RegisterTreatmentGoalStatusUseCase registerUseCase,
            GetTreatmentGoalStatusByIdUseCase getByIdUseCase,
            ListTreatmentGoalStatusUseCase listUseCase,
            UpdateTreatmentGoalStatusUseCase updateUseCase,
            DeleteTreatmentGoalStatusUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<TreatmentGoalStatusResponse> create(@Valid @RequestBody CreateTreatmentGoalStatusRequest request) {

        var command = new RegisterTreatmentGoalStatusCommand(request.code(), request.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentGoalStatusResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentGoalStatusResponse> findById(@PathVariable String id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId(java.util.UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentGoalStatusResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateTreatmentGoalStatusRequest request) {

        var command = new UpdateTreatmentGoalStatusCommand(request.code(), request.name());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}