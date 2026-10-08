package com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.in.rest.controllers;

import java.util.List;
import java.util.UUID;

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

import com.mindconnect.application.clinicalrecord.encounterstatus.command.RegisterEncounterStatusCommand;
import com.mindconnect.application.clinicalrecord.encounterstatus.command.UpdateEncounterStatusCommand;
import com.mindconnect.application.clinicalrecord.encounterstatus.dto.EncounterStatusResponse;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.DeleteEncounterStatusUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.GetEncounterStatusByIdUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.ListEncounterStatusUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.RegisterEncounterStatusUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.UpdateEncounterStatusUseCase;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.in.rest.dtos.CreateEncounterStatusRequest;
import com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.in.rest.dtos.UpdateEncounterStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/encounter-statuses")
public class EncounterStatusController {

    private final RegisterEncounterStatusUseCase registerUseCase;
    private final GetEncounterStatusByIdUseCase getByIdUseCase;
    private final ListEncounterStatusUseCase listUseCase;
    private final UpdateEncounterStatusUseCase updateUseCase;
    private final DeleteEncounterStatusUseCase deleteUseCase;

    public EncounterStatusController(
            RegisterEncounterStatusUseCase registerUseCase,
            GetEncounterStatusByIdUseCase getByIdUseCase,
            ListEncounterStatusUseCase listUseCase,
            UpdateEncounterStatusUseCase updateUseCase,
            DeleteEncounterStatusUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<EncounterStatusResponse> create(@Valid @RequestBody CreateEncounterStatusRequest request) {

        var command = new RegisterEncounterStatusCommand(request.code(), request.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<EncounterStatusResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncounterStatusResponse> findById(@PathVariable UUID id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new EncounterStatusId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EncounterStatusResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEncounterStatusRequest request) {

        var command = new UpdateEncounterStatusCommand(
                new EncounterStatusId(id),
                request.code(),
                request.name(),
                request.active());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new EncounterStatusId(id));

        return ResponseEntity.noContent().build();
    }
}