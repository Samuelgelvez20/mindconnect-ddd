package com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.in.rest.controllers;

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

import com.mindconnect.application.referencedata.citymunicipality.command.RegisterCityMunicipalityCommand;
import com.mindconnect.application.referencedata.citymunicipality.command.UpdateCityMunicipalityCommand;
import com.mindconnect.application.referencedata.citymunicipality.dto.CityMunicipalityResponse;
import com.mindconnect.application.referencedata.citymunicipality.usecase.DeleteCityMunicipalityUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.GetCityMunicipalityByIdUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.ListCityMunicipalityUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.RegisterCityMunicipalityUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.UpdateCityMunicipalityUseCase;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.in.rest.dtos.CreateCityMunicipalityRequest;
import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.in.rest.dtos.UpdateCityMunicipalityRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/city-municipalities")
public class CityMunicipalityController {

    private final RegisterCityMunicipalityUseCase registerUseCase;
    private final GetCityMunicipalityByIdUseCase getByIdUseCase;
    private final ListCityMunicipalityUseCase listUseCase;
    private final UpdateCityMunicipalityUseCase updateUseCase;
    private final DeleteCityMunicipalityUseCase deleteUseCase;

    public CityMunicipalityController(
            RegisterCityMunicipalityUseCase registerUseCase,
            GetCityMunicipalityByIdUseCase getByIdUseCase,
            ListCityMunicipalityUseCase listUseCase,
            UpdateCityMunicipalityUseCase updateUseCase,
            DeleteCityMunicipalityUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<CityMunicipalityResponse> create(@Valid @RequestBody CreateCityMunicipalityRequest request) {

        var command = new RegisterCityMunicipalityCommand(
                request.name(),
                request.code(),
                request.description(),
                new StateRegionId(request.regionId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<CityMunicipalityResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityMunicipalityResponse> findById(@PathVariable UUID id) {

        var cityMunicipalityId = new CityMunicipalityId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(cityMunicipalityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityMunicipalityResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCityMunicipalityRequest request) {

        var command = new UpdateCityMunicipalityCommand(
                new CityMunicipalityId(id),
                request.name(),
                request.code(),
                request.description(),
                request.active());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new CityMunicipalityId(id));

        return ResponseEntity.noContent().build();
    }
}