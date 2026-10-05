package com.mindconnect.application.contact.contact.usecase;

import com.mindconnect.application.contact.contact.command.RegisterContactCommand;
import com.mindconnect.application.contact.contact.dto.ContactResponse;
import com.mindconnect.domain.contact.contact.model.aggregate.Contact;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;

public class RegisterContactUseCase {

    private final ContactRepository contactRepository;

    public RegisterContactUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactResponse execute(RegisterContactCommand command) {

        Contact contact = Contact.register(
                command.fullName(),
                command.email(),
                command.notes(),
                command.cityId(),
                command.createdBy());

        return ContactResponse.from(contactRepository.save(contact));
    }
}