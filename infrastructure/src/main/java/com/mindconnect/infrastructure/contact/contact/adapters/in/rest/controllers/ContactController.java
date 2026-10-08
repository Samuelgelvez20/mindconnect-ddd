package com.mindconnect.infrastructure.contact.contact.adapters.in.rest.controllers;

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

import com.mindconnect.application.contact.contact.command.RegisterContactCommand;
import com.mindconnect.application.contact.contact.command.UpdateContactCommand;
import com.mindconnect.application.contact.contact.dto.ContactResponse;
import com.mindconnect.application.contact.contact.usecase.DeleteContactUseCase;
import com.mindconnect.application.contact.contact.usecase.GetContactByIdUseCase;
import com.mindconnect.application.contact.contact.usecase.ListContactUseCase;
import com.mindconnect.application.contact.contact.usecase.RegisterContactUseCase;
import com.mindconnect.application.contact.contact.usecase.UpdateContactUseCase;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.infrastructure.contact.contact.adapters.in.rest.dtos.CreateContactRequest;
import com.mindconnect.infrastructure.contact.contact.adapters.in.rest.dtos.UpdateContactRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final RegisterContactUseCase registerUseCase;
    private final GetContactByIdUseCase getByIdUseCase;
    private final ListContactUseCase listUseCase;
    private final UpdateContactUseCase updateUseCase;
    private final DeleteContactUseCase deleteUseCase;

    public ContactController(
            RegisterContactUseCase registerUseCase,
            GetContactByIdUseCase getByIdUseCase,
            ListContactUseCase listUseCase,
            UpdateContactUseCase updateUseCase,
            DeleteContactUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ContactResponse> create(@Valid @RequestBody CreateContactRequest request) {

        var command = new RegisterContactCommand(
                request.fullName(),
                request.email(),
                request.notes(),
                new CityMunicipalityId(request.cityId()),
                new ProfessionalId(request.createdBy()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> findAll() {

        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> findById(@PathVariable UUID id) {

        var contactId = new ContactId(id);

        return ResponseEntity.ok(getByIdUseCase.execute(contactId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateContactRequest request) {

        var command = new UpdateContactCommand(
                new ContactId(id),
                request.fullName(),
                request.email(),
                request.notes(),
                new CityMunicipalityId(request.cityId()),
                new ProfessionalId(request.updatedBy()));

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        deleteUseCase.execute(new ContactId(id));

        return ResponseEntity.noContent().build();
    }
}