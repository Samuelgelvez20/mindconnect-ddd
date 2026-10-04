package com.mindconnect.application.referencedata.citymunicipality.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.citymunicipality.command.UpdateCityMunicipalityCommand;
import com.mindconnect.application.referencedata.citymunicipality.dto.CityMunicipalityResponse;
import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

class UpdateCityMunicipalityUseCaseTest {

    @Test
    void shouldUpdateExistingCityMunicipality() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("Medellín", "MED", null, regionId);
        FakeCityMunicipalityRepository repository = new FakeCityMunicipalityRepository().with(cityMunicipality);
        UpdateCityMunicipalityUseCase useCase = new UpdateCityMunicipalityUseCase(repository);

        CityMunicipalityResponse response = useCase.execute(
                new UpdateCityMunicipalityCommand(cityMunicipality.id(), "Bogotá", "BOG", "Capital", false));

        assertEquals("Bogotá", response.name());
        assertEquals("BOG", response.code());
        assertEquals(false, response.active());
    }

    @Test
    void shouldAllowKeepingTheSameCode() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("Medellín", "MED", null, regionId);
        UpdateCityMunicipalityUseCase useCase =
                new UpdateCityMunicipalityUseCase(new FakeCityMunicipalityRepository().with(cityMunicipality));

        CityMunicipalityResponse response = useCase.execute(
                new UpdateCityMunicipalityCommand(cityMunicipality.id(), "Medellín 2", "MED", null, true));

        assertEquals("Medellín 2", response.name());
    }

    @Test
    void shouldRejectWhenCityMunicipalityDoesNotExist() {
        UpdateCityMunicipalityUseCase useCase = new UpdateCityMunicipalityUseCase(new FakeCityMunicipalityRepository());

        assertThrows(CityMunicipalityNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateCityMunicipalityCommand(CityMunicipalityId.generate(), "Bogotá", "BOG", null, true)));
    }

    @Test
    void shouldRejectCodeUsedByAnotherCityMunicipalityInSameRegion() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality medellin = CityMunicipality.register("Medellín", "MED", null, regionId);
        CityMunicipality bogota = CityMunicipality.register("Bogotá", "BOG", null, regionId);
        UpdateCityMunicipalityUseCase useCase =
                new UpdateCityMunicipalityUseCase(new FakeCityMunicipalityRepository().with(medellin, bogota));

        assertThrows(CityMunicipalityAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateCityMunicipalityCommand(bogota.id(), "Bogotá", "MED", null, true)));
    }
}