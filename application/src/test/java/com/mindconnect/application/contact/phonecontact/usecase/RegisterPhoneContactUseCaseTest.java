package com.mindconnect.application.contact.phonecontact.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.phonecontact.command.RegisterPhoneContactCommand;
import com.mindconnect.application.contact.phonecontact.dto.PhoneContactResponse;
import com.mindconnect.domain.contact.phonecontact.exception.InvalidPhoneContactException;
import com.mindconnect.domain.contact.phonecontact.model.aggregate.PhoneContact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

class RegisterPhoneContactUseCaseTest {

    @Test
    void shouldRegisterAndPersistPhoneContact() {
        FakePhoneContactRepository repository = new FakePhoneContactRepository();
        RegisterPhoneContactUseCase useCase = new RegisterPhoneContactUseCase(repository);

        PhoneContactResponse response = useCase.execute(
                new RegisterPhoneContactCommand(ContactId.generate(), "3001234567", "Mobile"));

        assertNotNull(response.id());
        assertEquals("3001234567", response.phone());
        assertEquals("Mobile", response.notes());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterPhoneContactUseCase useCase = new RegisterPhoneContactUseCase(new FakePhoneContactRepository());

        assertThrows(InvalidPhoneContactException.class,
                () -> useCase.execute(new RegisterPhoneContactCommand(ContactId.generate(), "  ", null)));
    }
}