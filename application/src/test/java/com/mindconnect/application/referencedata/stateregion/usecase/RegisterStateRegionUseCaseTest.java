package com.mindconnect.application.referencedata.stateregion.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.stateregion.command.RegisterStateRegionCommand;
import com.mindconnect.application.referencedata.stateregion.dto.StateRegionResponse;
import com.mindconnect.application.referencedata.stateregion.exception.StateRegionAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.exception.InvalidStateRegionException;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;

class RegisterStateRegionUseCaseTest {

    @Test
    void shouldRegisterAndPersistStateRegion() {
        CountryId countryId = CountryId.generate();
        FakeStateRegionRepository repository = new FakeStateRegionRepository();
        RegisterStateRegionUseCase useCase = new RegisterStateRegionUseCase(repository);

        StateRegionResponse response = useCase.execute(
                new RegisterStateRegionCommand("Antioquia", "ANT", "Departamento", countryId));

        assertNotNull(response.id());
        assertEquals("Antioquia", response.name());
        assertEquals("ANT", response.code());
        assertEquals(countryId.value(), response.countryId());
        assertTrue(response.active());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedCountryIdAndCode() {
        CountryId countryId = CountryId.generate();
        FakeStateRegionRepository repository =
                new FakeStateRegionRepository().with(StateRegion.register("Antioquia", "ANT", null, countryId));
        RegisterStateRegionUseCase useCase = new RegisterStateRegionUseCase(repository);

        assertThrows(StateRegionAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterStateRegionCommand("Otro", " ANT ", null, countryId)));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterStateRegionUseCase useCase = new RegisterStateRegionUseCase(new FakeStateRegionRepository());

        assertThrows(InvalidStateRegionException.class,
                () -> useCase.execute(new RegisterStateRegionCommand("  ", "ANT", null, CountryId.generate())));
    }
}