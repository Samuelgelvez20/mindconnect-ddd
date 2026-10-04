package com.mindconnect.application.referencedata.stateregion.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.stateregion.command.UpdateStateRegionCommand;
import com.mindconnect.application.referencedata.stateregion.dto.StateRegionResponse;
import com.mindconnect.application.referencedata.stateregion.exception.StateRegionAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.stateregion.exception.StateRegionNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

class UpdateStateRegionUseCaseTest {

    @Test
    void shouldUpdateExistingStateRegion() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("Antioquia", "ANT", null, countryId);
        FakeStateRegionRepository repository = new FakeStateRegionRepository().with(stateRegion);
        UpdateStateRegionUseCase useCase = new UpdateStateRegionUseCase(repository);

        StateRegionResponse response = useCase.execute(
                new UpdateStateRegionCommand(stateRegion.id(), "Cundinamarca", "CUN", "Departamento", false));

        assertEquals("Cundinamarca", response.name());
        assertEquals("CUN", response.code());
        assertEquals(false, response.active());
    }

    @Test
    void shouldAllowKeepingTheSameCode() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("Antioquia", "ANT", null, countryId);
        UpdateStateRegionUseCase useCase =
                new UpdateStateRegionUseCase(new FakeStateRegionRepository().with(stateRegion));

        StateRegionResponse response = useCase.execute(
                new UpdateStateRegionCommand(stateRegion.id(), "Antioquia 2", "ANT", null, true));

        assertEquals("Antioquia 2", response.name());
    }

    @Test
    void shouldRejectWhenStateRegionDoesNotExist() {
        UpdateStateRegionUseCase useCase = new UpdateStateRegionUseCase(new FakeStateRegionRepository());

        assertThrows(StateRegionNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateStateRegionCommand(StateRegionId.generate(), "Cundinamarca", "CUN", null, true)));
    }

    @Test
    void shouldRejectCodeUsedByAnotherStateRegionInSameCountry() {
        CountryId countryId = CountryId.generate();
        StateRegion antioquia = StateRegion.register("Antioquia", "ANT", null, countryId);
        StateRegion cundinamarca = StateRegion.register("Cundinamarca", "CUN", null, countryId);
        UpdateStateRegionUseCase useCase =
                new UpdateStateRegionUseCase(new FakeStateRegionRepository().with(antioquia, cundinamarca));

        assertThrows(StateRegionAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateStateRegionCommand(cundinamarca.id(), "Cundinamarca", "ANT", null, true)));
    }
}