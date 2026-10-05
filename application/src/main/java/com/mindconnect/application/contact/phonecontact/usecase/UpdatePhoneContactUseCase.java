package com.mindconnect.application.contact.phonecontact.usecase;

import com.mindconnect.application.contact.phonecontact.command.UpdatePhoneContactCommand;
import com.mindconnect.application.contact.phonecontact.dto.PhoneContactResponse;
import com.mindconnect.application.contact.phonecontact.exception.PhoneContactNotFoundApplicationException;
import com.mindconnect.domain.contact.phonecontact.port.repository.PhoneContactRepository;

public class UpdatePhoneContactUseCase {

    private final PhoneContactRepository phoneContactRepository;

    public UpdatePhoneContactUseCase(PhoneContactRepository phoneContactRepository) {
        this.phoneContactRepository = phoneContactRepository;
    }

    public PhoneContactResponse execute(UpdatePhoneContactCommand command) {

        var phoneContact = phoneContactRepository.findById(command.id())
                .orElseThrow(() -> new PhoneContactNotFoundApplicationException(command.id()));

        phoneContact.update(command.phone(), command.notes());

        return PhoneContactResponse.from(phoneContactRepository.save(phoneContact));
    }
}