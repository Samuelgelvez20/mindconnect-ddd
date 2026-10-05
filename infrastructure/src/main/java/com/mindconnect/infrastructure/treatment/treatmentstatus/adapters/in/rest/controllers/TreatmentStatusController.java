package com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.in.rest.controllers;

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

import com.mindconnect.application.treatment.treatmentstatus.command.RegisterTreatmentStatusCommand;
import com.mindconnect.application.treatment.treatmentstatus.command.UpdateTreatmentStatusCommand;
import com.mindconnect.application.treatment.treatmentstatus.dto.TreatmentStatusResponse;
import com.mindconnect.application.treatment.treatmentstatus.usecase.DeleteTreatmentStatusUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.GetTreatmentStatusByIdUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.ListTreatmentStatusUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.RegisterTreatmentStatusUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.UpdateTreatmentStatusUseCase;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.in.rest.dtos.CreateTreatmentStatusRequest;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.in.rest.dtos.UpdateTreatmentStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treatment-statuses")
public class TreatmentStatusController {

    private final RegisterTreatmentStatusUseCase registerUseCase;
    private final GetTreatmentStatusByIdUseCase getByIdUseCase;
    private final ListTreatmentStatusUseCase listUseCase;
    private final UpdateTreatmentStatusUseCase updateUseCase;
    private final DeleteTreatmentStatusUseCase deleteUseCase;

    public TreatmentStatusController(
            RegisterTreatmentStatusUseCase registerUseCase,
            GetTreatmentStatusByIdUseCase getByIdUseCase,
            ListTreatmentStatusUseCase listUseCase,
            UpdateTreatmentStatusUseCase updateUseCase,
            DeleteTreatmentStatusUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<TreatmentStatusResponse> create(@Valid @RequestBody CreateTreatmentStatusRequest request) {

        var command = new RegisterTreatmentStatusCommand(request.code(), request.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentStatusResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentStatusResponse> findById(@PathVariable String id) {

        var treatmentStatusId = new com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId(java.util.UUID.fromString(id));
        return ResponseEntity.ok(getByIdUseCase.execute(treatmentStatusId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentStatusResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateTreatmentStatusRequest request) {

        var command = new UpdateTreatmentStatusCommand(request.code(), request.name());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}