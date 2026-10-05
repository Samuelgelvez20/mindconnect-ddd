package com.mindconnect.application.contact.emailcontact.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.mindconnect.domain.contact.emailcontact.event.EmailContactDeletedEvent;
import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

class DeleteEmailContactUseCaseTest {

    @Test
    void shouldDeleteExistingEmailContactAndRecordEvent() {
        EmailContact emailContact = EmailContact.register(ContactId.generate(), "juan@example.com", "Personal");
        FakeEmailContactRepository repository = new FakeEmailContactRepository().with(emailContact);
        DeleteEmailContactUseCase useCase = new DeleteEmailContactUseCase(repository);

        useCase.execute(emailContact.id());

        assertEquals(1, repository.deleted().size());
        assertSame(emailContact, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(EmailContactDeletedEvent.class, emailContact.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenEmailContactDoesNotExist() {
        FakeEmailContactRepository repository = new FakeEmailContactRepository();
        DeleteEmailContactUseCase useCase = new DeleteEmailContactUseCase(repository);

        assertThrows(EmailContactNotFoundApplicationException.class,
                () -> useCase.execute(EmailContactId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}