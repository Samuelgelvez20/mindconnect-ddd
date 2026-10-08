package com.mindconnect.application.referencedata.country.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.country.command.RegisterCountryCommand;
import com.mindconnect.application.referencedata.country.dto.CountryResponse;
import com.mindconnect.application.referencedata.country.exception.CountryAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.country.exception.InvalidCountryException;
import com.mindconnect.domain.referencedata.country.model.aggregate.Country;

class RegisterCountryUseCaseTest {

    @Test
    void shouldRegisterAndPersistCountry() {
        FakeCountryRepository repository = new FakeCountryRepository();
        RegisterCountryUseCase useCase = new RegisterCountryUseCase(repository);

        CountryResponse response = useCase.execute(
                new RegisterCountryCommand("Colombia", "CO", "South America", "57"));

        assertNotNull(response.id());
        assertEquals("Colombia", response.name());
        assertEquals("CO", response.code());
        assertTrue(response.active());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedCode() {
        FakeCountryRepository repository =
                new FakeCountryRepository().with(Country.register("Colombia", "CO", null, null));
        RegisterCountryUseCase useCase = new RegisterCountryUseCase(repository);

        assertThrows(CountryAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterCountryCommand("Otro", " CO ", null, null)));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterCountryUseCase useCase = new RegisterCountryUseCase(new FakeCountryRepository());

        assertThrows(InvalidCountryException.class,
                () -> useCase.execute(new RegisterCountryCommand("  ", "CO", null, null)));
    }
}
