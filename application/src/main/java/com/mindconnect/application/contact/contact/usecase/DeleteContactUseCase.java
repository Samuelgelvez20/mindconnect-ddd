package com.mindconnect.application.contact.contact.usecase;

import com.mindconnect.application.contact.contact.exception.ContactNotFoundApplicationException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;

public class DeleteContactUseCase {

    private final ContactRepository contactRepository;

    public DeleteContactUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void execute(ContactId id) {

        var contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundApplicationException(id));

        contact.delete();
        contactRepository.delete(contact);
    }
}