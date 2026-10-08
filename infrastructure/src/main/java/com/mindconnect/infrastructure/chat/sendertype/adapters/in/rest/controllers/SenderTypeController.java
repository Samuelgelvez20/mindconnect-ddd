package com.mindconnect.infrastructure.chat.sendertype.adapters.in.rest.controllers;

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

import com.mindconnect.application.chat.sendertype.command.RegisterSenderTypeCommand;
import com.mindconnect.application.chat.sendertype.command.UpdateSenderTypeCommand;
import com.mindconnect.application.chat.sendertype.dto.SenderTypeResponse;
import com.mindconnect.application.chat.sendertype.usecase.DeleteSenderTypeUseCase;
import com.mindconnect.application.chat.sendertype.usecase.GetSenderTypeByIdUseCase;
import com.mindconnect.application.chat.sendertype.usecase.ListSenderTypeUseCase;
import com.mindconnect.application.chat.sendertype.usecase.RegisterSenderTypeUseCase;
import com.mindconnect.application.chat.sendertype.usecase.UpdateSenderTypeUseCase;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.infrastructure.chat.sendertype.adapters.in.rest.dtos.CreateSenderTypeRequest;
import com.mindconnect.infrastructure.chat.sendertype.adapters.in.rest.dtos.UpdateSenderTypeRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sender-types")
public class SenderTypeController {

    private final RegisterSenderTypeUseCase registerUseCase;
    private final GetSenderTypeByIdUseCase getByIdUseCase;
    private final ListSenderTypeUseCase listUseCase;
    private final UpdateSenderTypeUseCase updateUseCase;
    private final DeleteSenderTypeUseCase deleteUseCase;

    public SenderTypeController(
            RegisterSenderTypeUseCase registerUseCase,
            GetSenderTypeByIdUseCase getByIdUseCase,
            ListSenderTypeUseCase listUseCase,
            UpdateSenderTypeUseCase updateUseCase,
            DeleteSenderTypeUseCase deleteUseCase) {
        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<SenderTypeResponse> create(@Valid @RequestBody CreateSenderTypeRequest request) {
        var command = new RegisterSenderTypeCommand(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<SenderTypeResponse>> findAll() {
        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SenderTypeResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getByIdUseCase.execute(new SenderTypeId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SenderTypeResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSenderTypeRequest request) {

        var command = new UpdateSenderTypeCommand(
                new SenderTypeId(id),
                request.name(),
                request.active());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(new SenderTypeId(id));
        return ResponseEntity.noContent().build();
    }
}