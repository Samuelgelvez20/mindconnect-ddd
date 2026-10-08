package com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.in.rest.controllers;

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

import com.mindconnect.application.clinicalcatalog.consenttype.command.RegisterConsentTypeCommand;
import com.mindconnect.application.clinicalcatalog.consenttype.command.UpdateConsentTypeCommand;
import com.mindconnect.application.clinicalcatalog.consenttype.dto.ConsentTypeResponse;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.DeleteConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.GetConsentTypeByIdUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.ListConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.RegisterConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.UpdateConsentTypeUseCase;
import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.in.rest.dtos.CreateConsentTypeRequest;
import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.in.rest.dtos.UpdateConsentTypeRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/consent-types")
public class ConsentTypeController {

    private final RegisterConsentTypeUseCase registerUseCase;
    private final GetConsentTypeByIdUseCase getByIdUseCase;
    private final ListConsentTypeUseCase listUseCase;
    private final UpdateConsentTypeUseCase updateUseCase;
    private final DeleteConsentTypeUseCase deleteUseCase;

    public ConsentTypeController(
            RegisterConsentTypeUseCase registerUseCase,
            GetConsentTypeByIdUseCase getByIdUseCase,
            ListConsentTypeUseCase listUseCase,
            UpdateConsentTypeUseCase updateUseCase,
            DeleteConsentTypeUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ConsentTypeResponse> create(@Valid @RequestBody CreateConsentTypeRequest request) {

        var command = new RegisterConsentTypeCommand(request.code(), request.name(), request.description());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ConsentTypeResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsentTypeResponse> findById(@PathVariable String id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId(java.util.UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsentTypeResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateConsentTypeRequest request) {

        var command = new UpdateConsentTypeCommand(request.code(), request.name(), request.description(), request.active());
        var consentTypeId = new com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId(java.util.UUID.fromString(id));

        return ResponseEntity.ok(updateUseCase.execute(consentTypeId, command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}