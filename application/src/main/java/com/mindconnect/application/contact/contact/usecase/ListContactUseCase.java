package com.mindconnect.application.contact.contact.usecase;

import java.util.List;

import com.mindconnect.application.contact.contact.dto.ContactResponse;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;

public class ListContactUseCase {

    private final ContactRepository contactRepository;

    public ListContactUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactResponse> execute() {
        return contactRepository.findAll()
                .stream()
                .map(ContactResponse::from)
                .toList();
    }
}