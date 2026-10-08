package com.mindconnect.application.contact.emailcontact.usecase;

import com.mindconnect.application.contact.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;

public class DeleteEmailContactUseCase {

    private final EmailContactRepository emailContactRepository;

    public DeleteEmailContactUseCase(EmailContactRepository emailContactRepository) {
        this.emailContactRepository = emailContactRepository;
    }

    public void execute(EmailContactId id) {

        var emailContact = emailContactRepository.findById(id)
                .orElseThrow(() -> new EmailContactNotFoundApplicationException(id));

        emailContact.delete();
        emailContactRepository.delete(emailContact);
    }
}