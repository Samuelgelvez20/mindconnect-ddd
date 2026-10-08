package com.mindconnect.application.referencedata.country.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.country.exception.CountryNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.event.CountryDeletedEvent;
import com.mindconnect.domain.referencedata.country.model.aggregate.Country;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

class DeleteCountryUseCaseTest {

    @Test
    void shouldDeleteExistingCountryAndRecordEvent() {
        Country country = Country.register("Colombia", "CO", null, null);
        FakeCountryRepository repository = new FakeCountryRepository().with(country);
        DeleteCountryUseCase useCase = new DeleteCountryUseCase(repository);

        useCase.execute(country.id());

        assertEquals(1, repository.deleted().size());
        assertSame(country, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(CountryDeletedEvent.class, country.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenCountryDoesNotExist() {
        FakeCountryRepository repository = new FakeCountryRepository();
        DeleteCountryUseCase useCase = new DeleteCountryUseCase(repository);

        assertThrows(CountryNotFoundApplicationException.class,
                () -> useCase.execute(CountryId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}
