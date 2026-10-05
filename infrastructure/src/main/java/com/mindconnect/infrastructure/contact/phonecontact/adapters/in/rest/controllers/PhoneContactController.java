package com.mindconnect.infrastructure.contact.phonecontact.adapters.in.rest.controllers;

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

import com.mindconnect.application.contact.phonecontact.command.RegisterPhoneContactCommand;
import com.mindconnect.application.contact.phonecontact.command.UpdatePhoneContactCommand;
import com.mindconnect.application.contact.phonecontact.dto.PhoneContactResponse;
import com.mindconnect.application.contact.phonecontact.usecase.DeletePhoneContactUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.GetPhoneContactByIdUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.ListPhoneContactUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.RegisterPhoneContactUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.UpdatePhoneContactUseCase;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.infrastructure.contact.phonecontact.adapters.in.rest.dtos.CreatePhoneContactRequest;
import com.mindconnect.infrastructure.contact.phonecontact.adapters.in.rest.dtos.UpdatePhoneContactRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/phone-contacts")
public class PhoneContactController {

    private final RegisterPhoneContactUseCase registerUseCase;
    private final GetPhoneContactByIdUseCase getByIdUseCase;
    private final ListPhoneContactUseCase listUseCase;
    private final UpdatePhoneContactUseCase updateUseCase;
    private final DeletePhoneContactUseCase deleteUseCase;

    public PhoneContactController(
            RegisterPhoneContactUseCase registerUseCase,
            GetPhoneContactByIdUseCase getByIdUseCase,
            ListPhoneContactUseCase listUseCase,
            UpdatePhoneContactUseCase updateUseCase,
            DeletePhoneContactUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<PhoneContactResponse> create(@Valid @RequestBody CreatePhoneContactRequest request) {

        var command = new RegisterPhoneContactCommand(
                new ContactId(request.contactId()),
                request.phone(),
                request.notes());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<PhoneContactResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneContactResponse> findById(@PathVariable UUID id) {

        return ResponseEntity.ok(getByIdUseCase.execute(new PhoneContactId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhoneContactResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePhoneContactRequest request) {

        var command = new UpdatePhoneContactCommand(
                new PhoneContactId(id),
                request.phone(),
                request.notes());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new PhoneContactId(id));

        return ResponseEntity.noContent().build();
    }
}