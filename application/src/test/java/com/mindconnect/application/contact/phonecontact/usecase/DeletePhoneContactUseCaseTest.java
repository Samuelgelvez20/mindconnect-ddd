package com.mindconnect.application.contact.phonecontact.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.phonecontact.exception.PhoneContactNotFoundApplicationException;
import com.mindconnect.domain.contact.phonecontact.event.PhoneContactDeletedEvent;
import com.mindconnect.domain.contact.phonecontact.model.aggregate.PhoneContact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;

class DeletePhoneContactUseCaseTest {

    @Test
    void shouldDeleteExistingPhoneContactAndRecordEvent() {
        PhoneContact phoneContact = PhoneContact.register(ContactId.generate(), "3001234567", null);
        FakePhoneContactRepository repository = new FakePhoneContactRepository().with(phoneContact);
        DeletePhoneContactUseCase useCase = new DeletePhoneContactUseCase(repository);

        useCase.execute(phoneContact.id());

        assertEquals(1, repository.deleted().size());
        assertSame(phoneContact, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(PhoneContactDeletedEvent.class, phoneContact.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenPhoneContactDoesNotExist() {
        FakePhoneContactRepository repository = new FakePhoneContactRepository();
        DeletePhoneContactUseCase useCase = new DeletePhoneContactUseCase(repository);

        assertThrows(PhoneContactNotFoundApplicationException.class,
                () -> useCase.execute(PhoneContactId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}