package com.mindconnect.infrastructure.referencedata.stateregion.adapters.in.rest.controllers;

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

import com.mindconnect.application.referencedata.stateregion.command.RegisterStateRegionCommand;
import com.mindconnect.application.referencedata.stateregion.command.UpdateStateRegionCommand;
import com.mindconnect.application.referencedata.stateregion.dto.StateRegionResponse;
import com.mindconnect.application.referencedata.stateregion.usecase.DeleteStateRegionUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.GetStateRegionByIdUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.ListStateRegionUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.RegisterStateRegionUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.UpdateStateRegionUseCase;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.infrastructure.referencedata.stateregion.adapters.in.rest.dtos.CreateStateRegionRequest;
import com.mindconnect.infrastructure.referencedata.stateregion.adapters.in.rest.dtos.UpdateStateRegionRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/state-regions")
public class StateRegionController {

    private final RegisterStateRegionUseCase registerUseCase;
    private final GetStateRegionByIdUseCase getByIdUseCase;
    private final ListStateRegionUseCase listUseCase;
    private final UpdateStateRegionUseCase updateUseCase;
    private final DeleteStateRegionUseCase deleteUseCase;

    public StateRegionController(
            RegisterStateRegionUseCase registerUseCase,
            GetStateRegionByIdUseCase getByIdUseCase,
            ListStateRegionUseCase listUseCase,
            UpdateStateRegionUseCase updateUseCase,
            DeleteStateRegionUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<StateRegionResponse> create(@Valid @RequestBody CreateStateRegionRequest request) {

        var command = new RegisterStateRegionCommand(
                request.name(),
                request.code(),
                request.description(),
                new CountryId(request.countryId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<StateRegionResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateRegionResponse> findById(@PathVariable UUID id) {

        var stateRegionId = new StateRegionId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(stateRegionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateRegionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateStateRegionRequest request) {

        var command = new UpdateStateRegionCommand(
                new StateRegionId(id),
                request.name(),
                request.code(),
                request.description(),
                request.active());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new StateRegionId(id));

        return ResponseEntity.noContent().build();
    }
}