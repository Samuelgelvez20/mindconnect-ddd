package com.mindconnect.application.contact.phonecontact.usecase;

import com.mindconnect.application.contact.phonecontact.dto.PhoneContactResponse;
import com.mindconnect.application.contact.phonecontact.exception.PhoneContactNotFoundApplicationException;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;
import com.mindconnect.domain.contact.phonecontact.port.repository.PhoneContactRepository;

public class GetPhoneContactByIdUseCase {

    private final PhoneContactRepository phoneContactRepository;

    public GetPhoneContactByIdUseCase(PhoneContactRepository phoneContactRepository) {
        this.phoneContactRepository = phoneContactRepository;
    }

    public PhoneContactResponse execute(PhoneContactId id) {
        return phoneContactRepository.findById(id)
                .map(PhoneContactResponse::from)
                .orElseThrow(() -> new PhoneContactNotFoundApplicationException(id));
    }
}