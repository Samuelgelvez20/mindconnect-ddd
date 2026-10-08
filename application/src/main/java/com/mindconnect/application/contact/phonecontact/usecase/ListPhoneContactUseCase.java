package com.mindconnect.application.contact.phonecontact.usecase;

import java.util.List;

import com.mindconnect.application.contact.phonecontact.dto.PhoneContactResponse;
import com.mindconnect.domain.contact.phonecontact.port.repository.PhoneContactRepository;

public class ListPhoneContactUseCase {

    private final PhoneContactRepository phoneContactRepository;

    public ListPhoneContactUseCase(PhoneContactRepository phoneContactRepository) {
        this.phoneContactRepository = phoneContactRepository;
    }

    public List<PhoneContactResponse> execute() {
        return phoneContactRepository.findAll()
                .stream()
                .map(PhoneContactResponse::from)
                .toList();
    }
}