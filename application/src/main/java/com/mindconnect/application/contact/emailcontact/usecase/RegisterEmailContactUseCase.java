package com.mindconnect.application.contact.emailcontact.usecase;

import com.mindconnect.application.contact.emailcontact.command.RegisterEmailContactCommand;
import com.mindconnect.application.contact.emailcontact.dto.EmailContactResponse;
import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;

public class RegisterEmailContactUseCase {

    private final EmailContactRepository emailContactRepository;

    public RegisterEmailContactUseCase(EmailContactRepository emailContactRepository) {
        this.emailContactRepository = emailContactRepository;
    }

    public EmailContactResponse execute(RegisterEmailContactCommand command) {

        EmailContact emailContact = EmailContact.register(
                command.contactId(),
                command.email(),
                command.notes());

        return EmailContactResponse.from(emailContactRepository.save(emailContact));
    }
}