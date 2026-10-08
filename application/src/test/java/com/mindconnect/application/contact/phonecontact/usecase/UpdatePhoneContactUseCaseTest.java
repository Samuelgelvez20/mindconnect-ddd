package com.mindconnect.application.contact.phonecontact.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.phonecontact.command.UpdatePhoneContactCommand;
import com.mindconnect.application.contact.phonecontact.dto.PhoneContactResponse;
import com.mindconnect.application.contact.phonecontact.exception.PhoneContactNotFoundApplicationException;
import com.mindconnect.domain.contact.phonecontact.model.aggregate.PhoneContact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;

class UpdatePhoneContactUseCaseTest {

    @Test
    void shouldUpdateExistingPhoneContact() {
        PhoneContact phoneContact = PhoneContact.register(ContactId.generate(), "3001234567", "Mobile");
        FakePhoneContactRepository repository = new FakePhoneContactRepository().with(phoneContact);
        UpdatePhoneContactUseCase useCase = new UpdatePhoneContactUseCase(repository);

        PhoneContactResponse response = useCase.execute(
                new UpdatePhoneContactCommand(phoneContact.id(), "3109876543", "Work"));

        assertEquals("3109876543", response.phone());
        assertEquals("Work", response.notes());
    }

    @Test
    void shouldRejectWhenPhoneContactDoesNotExist() {
        UpdatePhoneContactUseCase useCase = new UpdatePhoneContactUseCase(new FakePhoneContactRepository());

        assertThrows(PhoneContactNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdatePhoneContactCommand(PhoneContactId.generate(), "3109876543", "Work")));
    }
}