package com.mindconnect.infrastructure.professional.study.adapters.in.rest.controllers;

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

import com.mindconnect.application.professional.study.command.RegisterStudyCommand;
import com.mindconnect.application.professional.study.command.UpdateStudyCommand;
import com.mindconnect.application.professional.study.dto.StudyResponse;
import com.mindconnect.application.professional.study.usecase.DeleteStudyUseCase;
import com.mindconnect.application.professional.study.usecase.GetStudyByIdUseCase;
import com.mindconnect.application.professional.study.usecase.ListStudyUseCase;
import com.mindconnect.application.professional.study.usecase.RegisterStudyUseCase;
import com.mindconnect.application.professional.study.usecase.UpdateStudyUseCase;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.infrastructure.professional.study.adapters.in.rest.dtos.CreateStudyRequest;
import com.mindconnect.infrastructure.professional.study.adapters.in.rest.dtos.UpdateStudyRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/studies")
public class StudyController {

    private final RegisterStudyUseCase registerUseCase;
    private final GetStudyByIdUseCase getByIdUseCase;
    private final ListStudyUseCase listUseCase;
    private final UpdateStudyUseCase updateUseCase;
    private final DeleteStudyUseCase deleteUseCase;

    public StudyController(
            RegisterStudyUseCase registerUseCase,
            GetStudyByIdUseCase getByIdUseCase,
            ListStudyUseCase listUseCase,
            UpdateStudyUseCase updateUseCase,
            DeleteStudyUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<StudyResponse> create(@Valid @RequestBody CreateStudyRequest request) {

        var command = new RegisterStudyCommand(request.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<StudyResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyResponse> findById(@PathVariable UUID id) {

        var studyId = new StudyId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(studyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateStudyRequest request) {

        var command = new UpdateStudyCommand(
                new StudyId(id),
                request.name());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new StudyId(id));

        return ResponseEntity.noContent().build();
    }
}