package com.mindconnect.application.contact.emailcontact.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.emailcontact.command.UpdateEmailContactCommand;
import com.mindconnect.application.contact.emailcontact.dto.EmailContactResponse;
import com.mindconnect.application.contact.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

class UpdateEmailContactUseCaseTest {

    @Test
    void shouldUpdateExistingEmailContact() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "juan@example.com", "Personal");
        FakeEmailContactRepository repository = new FakeEmailContactRepository().with(emailContact);
        UpdateEmailContactUseCase useCase = new UpdateEmailContactUseCase(repository);

        EmailContactResponse response = useCase.execute(
                new UpdateEmailContactCommand(emailContact.id(), "maria@example.com", "Work"));

        assertEquals("maria@example.com", response.email());
        assertEquals("Work", response.notes());
    }

    @Test
    void shouldRejectWhenEmailContactDoesNotExist() {
        UpdateEmailContactUseCase useCase = new UpdateEmailContactUseCase(new FakeEmailContactRepository());

        assertThrows(EmailContactNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateEmailContactCommand(EmailContactId.generate(), "maria@example.com", "Work")));
    }
}