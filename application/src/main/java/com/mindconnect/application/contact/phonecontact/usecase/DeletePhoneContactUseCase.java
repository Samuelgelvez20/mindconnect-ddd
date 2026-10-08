package com.mindconnect.application.contact.phonecontact.usecase;

import com.mindconnect.application.contact.phonecontact.exception.PhoneContactNotFoundApplicationException;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;
import com.mindconnect.domain.contact.phonecontact.port.repository.PhoneContactRepository;

public class DeletePhoneContactUseCase {

    private final PhoneContactRepository phoneContactRepository;

    public DeletePhoneContactUseCase(PhoneContactRepository phoneContactRepository) {
        this.phoneContactRepository = phoneContactRepository;
    }

    public void execute(PhoneContactId id) {

        var phoneContact = phoneContactRepository.findById(id)
                .orElseThrow(() -> new PhoneContactNotFoundApplicationException(id));

        phoneContact.delete();
        phoneContactRepository.delete(phoneContact);
    }
}