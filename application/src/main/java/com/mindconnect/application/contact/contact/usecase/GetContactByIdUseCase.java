package com.mindconnect.application.contact.contact.usecase;

import com.mindconnect.application.contact.contact.dto.ContactResponse;
import com.mindconnect.application.contact.contact.exception.ContactNotFoundApplicationException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;

public class GetContactByIdUseCase {

    private final ContactRepository contactRepository;

    public GetContactByIdUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactResponse execute(ContactId id) {
        return contactRepository.findById(id)
                .map(ContactResponse::from)
                .orElseThrow(() -> new ContactNotFoundApplicationException(id));
    }
}