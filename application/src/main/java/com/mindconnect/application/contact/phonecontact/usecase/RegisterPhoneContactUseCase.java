package com.mindconnect.application.contact.phonecontact.usecase;

import com.mindconnect.application.contact.phonecontact.command.RegisterPhoneContactCommand;
import com.mindconnect.application.contact.phonecontact.dto.PhoneContactResponse;
import com.mindconnect.domain.contact.phonecontact.model.aggregate.PhoneContact;
import com.mindconnect.domain.contact.phonecontact.port.repository.PhoneContactRepository;

public class RegisterPhoneContactUseCase {

    private final PhoneContactRepository phoneContactRepository;

    public RegisterPhoneContactUseCase(PhoneContactRepository phoneContactRepository) {
        this.phoneContactRepository = phoneContactRepository;
    }

    public PhoneContactResponse execute(RegisterPhoneContactCommand command) {

        PhoneContact phoneContact = PhoneContact.register(
                command.contactId(),
                command.phone(),
                command.notes());

        return PhoneContactResponse.from(phoneContactRepository.save(phoneContact));
    }
}