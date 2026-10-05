package com.mindconnect.application.contact.emailcontact.usecase;

import com.mindconnect.application.contact.emailcontact.command.UpdateEmailContactCommand;
import com.mindconnect.application.contact.emailcontact.dto.EmailContactResponse;
import com.mindconnect.application.contact.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;

public class UpdateEmailContactUseCase {

    private final EmailContactRepository emailContactRepository;

    public UpdateEmailContactUseCase(EmailContactRepository emailContactRepository) {
        this.emailContactRepository = emailContactRepository;
    }

    public EmailContactResponse execute(UpdateEmailContactCommand command) {

        var emailContact = emailContactRepository.findById(command.id())
                .orElseThrow(() -> new EmailContactNotFoundApplicationException(command.id()));

        emailContact.update(command.email(), command.notes());

        return EmailContactResponse.from(emailContactRepository.save(emailContact));
    }
}