package com.mindconnect.infrastructure.professional.professionalstudy.adapters.in.rest.controllers;

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

import com.mindconnect.application.professional.professionalstudy.command.RegisterProfessionalStudyCommand;
import com.mindconnect.application.professional.professionalstudy.command.UpdateProfessionalStudyCommand;
import com.mindconnect.application.professional.professionalstudy.dto.ProfessionalStudyResponse;
import com.mindconnect.application.professional.professionalstudy.usecase.DeleteProfessionalStudyUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.GetProfessionalStudyByIdUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.ListProfessionalStudyUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.RegisterProfessionalStudyUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.UpdateProfessionalStudyUseCase;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.infrastructure.professional.professionalstudy.adapters.in.rest.dtos.CreateProfessionalStudyRequest;
import com.mindconnect.infrastructure.professional.professionalstudy.adapters.in.rest.dtos.UpdateProfessionalStudyRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professional-studies")
public class ProfessionalStudyController {

    private final RegisterProfessionalStudyUseCase registerUseCase;
    private final GetProfessionalStudyByIdUseCase getByIdUseCase;
    private final ListProfessionalStudyUseCase listUseCase;
    private final UpdateProfessionalStudyUseCase updateUseCase;
    private final DeleteProfessionalStudyUseCase deleteUseCase;

    public ProfessionalStudyController(
            RegisterProfessionalStudyUseCase registerUseCase,
            GetProfessionalStudyByIdUseCase getByIdUseCase,
            ListProfessionalStudyUseCase listUseCase,
            UpdateProfessionalStudyUseCase updateUseCase,
            DeleteProfessionalStudyUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ProfessionalStudyResponse> create(@Valid @RequestBody CreateProfessionalStudyRequest request) {

        var command = new RegisterProfessionalStudyCommand(
                new StudyId(request.studyId()),
                new ProfessionalId(request.professionalId()),
                request.title(),
                request.university(),
                new CountryId(request.countryId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalStudyResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalStudyResponse> findById(@PathVariable UUID id) {

        var professionalStudyId = new ProfessionalStudyId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(professionalStudyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionalStudyResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProfessionalStudyRequest request) {

        var command = new UpdateProfessionalStudyCommand(
                new ProfessionalStudyId(id),
                request.title(),
                request.university(),
                request.isValid(),
                request.resolutionNumber(),
                new CountryId(request.countryId()));

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new ProfessionalStudyId(id));

        return ResponseEntity.noContent().build();
    }
}