package com.mindconnect.application.referencedata.country.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.country.command.UpdateCountryCommand;
import com.mindconnect.application.referencedata.country.dto.CountryResponse;
import com.mindconnect.application.referencedata.country.exception.CountryAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.country.exception.CountryNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.model.aggregate.Country;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

class UpdateCountryUseCaseTest {

    @Test
    void shouldUpdateExistingCountry() {
        Country country = Country.register("Colombia", "CO", null, null);
        FakeCountryRepository repository = new FakeCountryRepository().with(country);
        UpdateCountryUseCase useCase = new UpdateCountryUseCase(repository);

        CountryResponse response = useCase.execute(
                new UpdateCountryCommand(country.id(), "Peru", "PE", "Andean", "51", false));

        assertEquals("Peru", response.name());
        assertEquals("PE", response.code());
        assertFalse(response.active());
    }

    @Test
    void shouldAllowKeepingTheSameCode() {
        Country country = Country.register("Colombia", "CO", null, null);
        UpdateCountryUseCase useCase =
                new UpdateCountryUseCase(new FakeCountryRepository().with(country));

        CountryResponse response = useCase.execute(
                new UpdateCountryCommand(country.id(), "Colombia 2", "CO", null, null, true));

        assertEquals("Colombia 2", response.name());
    }

    @Test
    void shouldRejectWhenCountryDoesNotExist() {
        UpdateCountryUseCase useCase = new UpdateCountryUseCase(new FakeCountryRepository());

        assertThrows(CountryNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateCountryCommand(CountryId.generate(), "Peru", "PE", null, null, true)));
    }

    @Test
    void shouldRejectCodeUsedByAnotherCountry() {
        Country colombia = Country.register("Colombia", "CO", null, null);
        Country peru = Country.register("Peru", "PE", null, null);
        UpdateCountryUseCase useCase =
                new UpdateCountryUseCase(new FakeCountryRepository().with(colombia, peru));

        assertThrows(CountryAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateCountryCommand(peru.id(), "Peru", "CO", null, null, true)));
    }
}
