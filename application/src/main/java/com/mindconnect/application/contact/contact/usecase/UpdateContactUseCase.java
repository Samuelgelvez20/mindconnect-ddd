package com.mindconnect.application.contact.contact.usecase;

import com.mindconnect.application.contact.contact.command.UpdateContactCommand;
import com.mindconnect.application.contact.contact.dto.ContactResponse;
import com.mindconnect.application.contact.contact.exception.ContactNotFoundApplicationException;
import com.mindconnect.domain.contact.contact.model.aggregate.Contact;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;

public class UpdateContactUseCase {

    private final ContactRepository contactRepository;

    public UpdateContactUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactResponse execute(UpdateContactCommand command) {

        var contact = contactRepository.findById(command.id())
                .orElseThrow(() -> new ContactNotFoundApplicationException(command.id()));

        contact.update(
                command.fullName(),
                command.email(),
                command.notes(),
                command.cityId(),
                command.updatedBy());

        return ContactResponse.from(contactRepository.save(contact));
    }
}