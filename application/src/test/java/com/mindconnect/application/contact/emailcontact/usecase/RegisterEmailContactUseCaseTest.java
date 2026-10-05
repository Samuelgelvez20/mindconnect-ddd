package com.mindconnect.application.contact.emailcontact.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.emailcontact.command.RegisterEmailContactCommand;
import com.mindconnect.application.contact.emailcontact.dto.EmailContactResponse;
import com.mindconnect.domain.contact.emailcontact.exception.InvalidEmailContactException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

class RegisterEmailContactUseCaseTest {

    @Test
    void shouldRegisterAndPersistEmailContact() {
        FakeEmailContactRepository repository = new FakeEmailContactRepository();
        RegisterEmailContactUseCase useCase = new RegisterEmailContactUseCase(repository);

        EmailContactResponse response = useCase.execute(
                new RegisterEmailContactCommand(ContactId.generate(), "juan@example.com", "Personal"));

        assertNotNull(response.id());
        assertEquals("juan@example.com", response.email());
        assertEquals("Personal", response.notes());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterEmailContactUseCase useCase = new RegisterEmailContactUseCase(new FakeEmailContactRepository());

        assertThrows(InvalidEmailContactException.class,
                () -> useCase.execute(new RegisterEmailContactCommand(
                        ContactId.generate(), "  ", "University")));
    }
}