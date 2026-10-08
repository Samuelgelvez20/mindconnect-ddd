package com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.in.rest.controllers;

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

import com.mindconnect.application.clinicalcatalog.medicationroute.command.RegisterMedicationRouteCommand;
import com.mindconnect.application.clinicalcatalog.medicationroute.command.UpdateMedicationRouteCommand;
import com.mindconnect.application.clinicalcatalog.medicationroute.dto.MedicationRouteResponse;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.DeleteMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.GetMedicationRouteByIdUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.ListMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.RegisterMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.UpdateMedicationRouteUseCase;
import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.in.rest.dtos.CreateMedicationRouteRequest;
import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.in.rest.dtos.UpdateMedicationRouteRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medication-routes")
public class MedicationRouteController {

    private final RegisterMedicationRouteUseCase registerUseCase;
    private final GetMedicationRouteByIdUseCase getByIdUseCase;
    private final ListMedicationRouteUseCase listUseCase;
    private final UpdateMedicationRouteUseCase updateUseCase;
    private final DeleteMedicationRouteUseCase deleteUseCase;

    public MedicationRouteController(
            RegisterMedicationRouteUseCase registerUseCase,
            GetMedicationRouteByIdUseCase getByIdUseCase,
            ListMedicationRouteUseCase listUseCase,
            UpdateMedicationRouteUseCase updateUseCase,
            DeleteMedicationRouteUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<MedicationRouteResponse> create(@Valid @RequestBody CreateMedicationRouteRequest request) {

        var command = new RegisterMedicationRouteCommand(request.code(), request.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<MedicationRouteResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationRouteResponse> findById(@PathVariable String id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId(java.util.UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationRouteResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateMedicationRouteRequest request) {

        var command = new UpdateMedicationRouteCommand(request.code(), request.name(), request.active());
        var medicationRouteId = new com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId(java.util.UUID.fromString(id));

        return ResponseEntity.ok(updateUseCase.execute(medicationRouteId, command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        deleteUseCase.execute(new com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId(java.util.UUID.fromString(id)));

        return ResponseEntity.noContent().build();
    }
}