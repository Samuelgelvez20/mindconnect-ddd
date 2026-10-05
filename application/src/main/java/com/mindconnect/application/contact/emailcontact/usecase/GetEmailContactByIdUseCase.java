package com.mindconnect.application.contact.emailcontact.usecase;

import com.mindconnect.application.contact.emailcontact.dto.EmailContactResponse;
import com.mindconnect.application.contact.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;

public class GetEmailContactByIdUseCase {

    private final EmailContactRepository emailContactRepository;

    public GetEmailContactByIdUseCase(EmailContactRepository emailContactRepository) {
        this.emailContactRepository = emailContactRepository;
    }

    public EmailContactResponse execute(EmailContactId id) {
        return emailContactRepository.findById(id)
                .map(EmailContactResponse::from)
                .orElseThrow(() -> new EmailContactNotFoundApplicationException(id));
    }
}