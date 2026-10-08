package com.mindconnect.infrastructure.referencedata.gender.adapters.in.rest.controllers;

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

import com.mindconnect.application.referencedata.gender.command.RegisterGenderCommand;
import com.mindconnect.application.referencedata.gender.command.UpdateGenderCommand;
import com.mindconnect.application.referencedata.gender.dto.GenderResponse;
import com.mindconnect.application.referencedata.gender.usecase.DeleteGenderUseCase;
import com.mindconnect.application.referencedata.gender.usecase.GetGenderByIdUseCase;
import com.mindconnect.application.referencedata.gender.usecase.ListGenderUseCase;
import com.mindconnect.application.referencedata.gender.usecase.RegisterGenderUseCase;
import com.mindconnect.application.referencedata.gender.usecase.UpdateGenderUseCase;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;
import com.mindconnect.infrastructure.referencedata.gender.adapters.in.rest.dtos.CreateGenderRequest;
import com.mindconnect.infrastructure.referencedata.gender.adapters.in.rest.dtos.UpdateGenderRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genders")
public class GenderController {

    private final RegisterGenderUseCase registerUseCase;
    private final GetGenderByIdUseCase getByIdUseCase;
    private final ListGenderUseCase listUseCase;
    private final UpdateGenderUseCase updateUseCase;
    private final DeleteGenderUseCase deleteUseCase;

    public GenderController(
            RegisterGenderUseCase registerUseCase,
            GetGenderByIdUseCase getByIdUseCase,
            ListGenderUseCase listUseCase,
            UpdateGenderUseCase updateUseCase,
            DeleteGenderUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<GenderResponse> create(@Valid @RequestBody CreateGenderRequest request) {

        var command = new RegisterGenderCommand(request.description());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<GenderResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenderResponse> findById(@PathVariable UUID id) {

        var genderId = new GenderId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(genderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenderResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateGenderRequest request) {

        var command = new UpdateGenderCommand(
                new GenderId(id),
                request.description());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new GenderId(id));

        return ResponseEntity.noContent().build();
    }
}