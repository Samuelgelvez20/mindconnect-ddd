package com.mindconnect.infrastructure.patient.patientallergy.adapters.in.rest.controllers;

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

import com.mindconnect.application.patient.patientallergy.command.RegisterPatientAllergyCommand;
import com.mindconnect.application.patient.patientallergy.command.UpdatePatientAllergyCommand;
import com.mindconnect.application.patient.patientallergy.dto.PatientAllergyResponse;
import com.mindconnect.application.patient.patientallergy.usecase.DeletePatientAllergyUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.GetPatientAllergyByIdUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.ListPatientAllergyUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.RegisterPatientAllergyUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.UpdatePatientAllergyUseCase;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.patient.patientallergy.adapters.in.rest.dtos.CreatePatientAllergyRequest;
import com.mindconnect.infrastructure.patient.patientallergy.adapters.in.rest.dtos.UpdatePatientAllergyRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient-allergies")
public class PatientAllergyController {

    private final RegisterPatientAllergyUseCase registerUseCase;
    private final GetPatientAllergyByIdUseCase getByIdUseCase;
    private final ListPatientAllergyUseCase listUseCase;
    private final UpdatePatientAllergyUseCase updateUseCase;
    private final DeletePatientAllergyUseCase deleteUseCase;

    public PatientAllergyController(
            RegisterPatientAllergyUseCase registerUseCase,
            GetPatientAllergyByIdUseCase getByIdUseCase,
            ListPatientAllergyUseCase listUseCase,
            UpdatePatientAllergyUseCase updateUseCase,
            DeletePatientAllergyUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<PatientAllergyResponse> create(@Valid @RequestBody CreatePatientAllergyRequest request) {

        var command = new RegisterPatientAllergyCommand(
                new PatientId(request.patientId()),
                request.substance(),
                request.reaction(),
                request.severity(),
                new ProfessionalId(request.recordedBy()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<PatientAllergyResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientAllergyResponse> findById(@PathVariable UUID id) {

        var patientAllergyId = new PatientAllergyId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(patientAllergyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientAllergyResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePatientAllergyRequest request) {

        var command = new UpdatePatientAllergyCommand(
                new PatientAllergyId(id),
                request.substance(),
                request.reaction(),
                request.severity(),
                request.active());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new PatientAllergyId(id));

        return ResponseEntity.noContent().build();
    }
}