package com.mindconnect.application.contact.emailcontact.usecase;

import java.util.List;

import com.mindconnect.application.contact.emailcontact.dto.EmailContactResponse;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;

public class ListEmailContactUseCase {

    private final EmailContactRepository emailContactRepository;

    public ListEmailContactUseCase(EmailContactRepository emailContactRepository) {
        this.emailContactRepository = emailContactRepository;
    }

    public List<EmailContactResponse> execute() {
        return emailContactRepository.findAll()
                .stream()
                .map(EmailContactResponse::from)
                .toList();
    }
}