package com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.in.rest.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.mindconnect.application.clinicalrecord.mentalstatusexam.command.RegisterMentalStatusExamCommand;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.command.UpdateMentalStatusExamCommand;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.dto.MentalStatusExamResponse;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.DeleteMentalStatusExamUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.GetMentalStatusExamByIdUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.ListMentalStatusExamUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.RegisterMentalStatusExamUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.UpdateMentalStatusExamUseCase;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.in.rest.dtos.CreateMentalStatusExamRequest;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.in.rest.dtos.UpdateMentalStatusExamRequest;

@RestController
@RequestMapping("/api/mental-status-exams")
public class MentalStatusExamController {

    private final RegisterMentalStatusExamUseCase registerUseCase;
    private final GetMentalStatusExamByIdUseCase getByIdUseCase;
    private final ListMentalStatusExamUseCase listUseCase;
    private final UpdateMentalStatusExamUseCase updateUseCase;
    private final DeleteMentalStatusExamUseCase deleteUseCase;

    public MentalStatusExamController(
            RegisterMentalStatusExamUseCase registerUseCase,
            GetMentalStatusExamByIdUseCase getByIdUseCase,
            ListMentalStatusExamUseCase listUseCase,
            UpdateMentalStatusExamUseCase updateUseCase,
            DeleteMentalStatusExamUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<MentalStatusExamResponse> create(@Valid @RequestBody CreateMentalStatusExamRequest request) {

        var command = new RegisterMentalStatusExamCommand(
                new EncounterId(request.encounterId()),
                request.appearance(),
                request.behavior(),
                request.attitude(),
                request.consciousness(),
                request.orientation(),
                request.attention(),
                request.memory(),
                request.speech(),
                request.mood(),
                request.affect(),
                request.thoughtProcess(),
                request.thoughtContent(),
                request.perception(),
                request.judgment(),
                request.insight(),
                request.psychomotorActivity(),
                request.observations(),
                new ProfessionalId(request.createdBy()));

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<MentalStatusExamResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentalStatusExamResponse> findById(@PathVariable UUID id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new MentalStatusExamId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentalStatusExamResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMentalStatusExamRequest request) {

        var command = new UpdateMentalStatusExamCommand(
                new MentalStatusExamId(id),
                request.encounterId() != null ? new EncounterId(request.encounterId()) : null,
                request.appearance(),
                request.behavior(),
                request.attitude(),
                request.consciousness(),
                request.orientation(),
                request.attention(),
                request.memory(),
                request.speech(),
                request.mood(),
                request.affect(),
                request.thoughtProcess(),
                request.thoughtContent(),
                request.perception(),
                request.judgment(),
                request.insight(),
                request.psychomotorActivity(),
                request.observations(),
                request.createdBy() != null ? new ProfessionalId(request.createdBy()) : null);

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new MentalStatusExamId(id));

        return ResponseEntity.noContent().build();
    }
}