package com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.in.rest.controllers;

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

import com.mindconnect.application.clinicalcatalog.diagnosticsystem.command.RegisterDiagnosticSystemCommand;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.command.UpdateDiagnosticSystemCommand;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.ListDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase;
import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.in.rest.dtos.CreateDiagnosticSystemRequest;
import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.in.rest.dtos.UpdateDiagnosticSystemRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/diagnostic-systems")
public class DiagnosticSystemController {

    private final RegisterDiagnosticSystemUseCase registerUseCase;
    private final GetDiagnosticSystemByIdUseCase getByIdUseCase;
    private final ListDiagnosticSystemUseCase listUseCase;
    private final UpdateDiagnosticSystemUseCase updateUseCase;
    private final DeleteDiagnosticSystemUseCase deleteUseCase;

    public DiagnosticSystemController(
            RegisterDiagnosticSystemUseCase registerUseCase,
            GetDiagnosticSystemByIdUseCase getByIdUseCase,
            ListDiagnosticSystemUseCase listUseCase,
            UpdateDiagnosticSystemUseCase updateUseCase,
            DeleteDiagnosticSystemUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<DiagnosticSystemResponse> create(@Valid @RequestBody CreateDiagnosticSystemRequest request) {

        var command = new RegisterDiagnosticSystemCommand(request.code(), request.name(), request.version());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<DiagnosticSystemResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticSystemResponse> findById(@PathVariable String id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId(java.util.UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticSystemResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateDiagnosticSystemRequest request) {

        var command = new UpdateDiagnosticSystemCommand(request.code(), request.name(), request.version(), request.active());
        var diagnosticSystemId = new com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId(java.util.UUID.fromString(id));

        return ResponseEntity.ok(updateUseCase.execute(diagnosticSystemId, command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}