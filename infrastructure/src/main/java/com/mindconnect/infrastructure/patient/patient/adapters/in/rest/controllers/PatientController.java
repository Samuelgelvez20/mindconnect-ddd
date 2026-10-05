package com.mindconnect.infrastructure.patient.patient.adapters.in.rest.controllers;

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

import com.mindconnect.application.patient.patient.command.RegisterPatientCommand;
import com.mindconnect.application.patient.patient.command.UpdatePatientCommand;
import com.mindconnect.application.patient.patient.dto.PatientResponse;
import com.mindconnect.application.patient.patient.usecase.DeletePatientUseCase;
import com.mindconnect.application.patient.patient.usecase.GetPatientByIdUseCase;
import com.mindconnect.application.patient.patient.usecase.ListPatientUseCase;
import com.mindconnect.application.patient.patient.usecase.RegisterPatientUseCase;
import com.mindconnect.application.patient.patient.usecase.UpdatePatientUseCase;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;
import com.mindconnect.infrastructure.patient.patient.adapters.in.rest.dtos.CreatePatientRequest;
import com.mindconnect.infrastructure.patient.patient.adapters.in.rest.dtos.UpdatePatientRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final RegisterPatientUseCase registerUseCase;
    private final GetPatientByIdUseCase getByIdUseCase;
    private final ListPatientUseCase listUseCase;
    private final UpdatePatientUseCase updateUseCase;
    private final DeletePatientUseCase deleteUseCase;

    public PatientController(
            RegisterPatientUseCase registerUseCase,
            GetPatientByIdUseCase getByIdUseCase,
            ListPatientUseCase listUseCase,
            UpdatePatientUseCase updateUseCase,
            DeletePatientUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody CreatePatientRequest request) {

        var command = new RegisterPatientCommand(
                new DocumentTypeId(request.documentTypeId()),
                request.documentNumber(),
                request.firstName(),
                request.middleName(),
                request.lastName(),
                request.secondLastName(),
                request.birthDate(),
                new GenderId(request.biologicalSexId()),
                new GenderId(request.genderIdentityId()),
                request.email(),
                request.phone(),
                request.address(),
                new ProfessionalId(request.createdBy()),
                new CityMunicipalityId(request.cityId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable UUID id) {

        var patientId = new PatientId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(patientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePatientRequest request) {

        var command = new UpdatePatientCommand(
                new PatientId(id),
                new DocumentTypeId(request.documentTypeId()),
                request.documentNumber(),
                request.firstName(),
                request.middleName(),
                request.lastName(),
                request.secondLastName(),
                request.birthDate(),
                new GenderId(request.biologicalSexId()),
                new GenderId(request.genderIdentityId()),
                request.email(),
                request.phone(),
                request.address(),
                request.active(),
                new ProfessionalId(request.updatedBy()),
                new CityMunicipalityId(request.cityId()));

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new PatientId(id));

        return ResponseEntity.noContent().build();
    }
}