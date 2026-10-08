package com.mindconnect.application.referencedata.citymunicipality.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.citymunicipality.command.RegisterCityMunicipalityCommand;
import com.mindconnect.application.referencedata.citymunicipality.dto.CityMunicipalityResponse;
import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.citymunicipality.exception.InvalidCityMunicipalityException;
import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

class RegisterCityMunicipalityUseCaseTest {

    @Test
    void shouldRegisterAndPersistCityMunicipality() {
        StateRegionId regionId = StateRegionId.generate();
        FakeCityMunicipalityRepository repository = new FakeCityMunicipalityRepository();
        RegisterCityMunicipalityUseCase useCase = new RegisterCityMunicipalityUseCase(repository);

        CityMunicipalityResponse response = useCase.execute(
                new RegisterCityMunicipalityCommand("Medellín", "MED", "Ciudad", regionId));

        assertNotNull(response.id());
        assertEquals("Medellín", response.name());
        assertEquals("MED", response.code());
        assertEquals(regionId.value(), response.regionId());
        assertTrue(response.active());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedRegionIdAndCode() {
        StateRegionId regionId = StateRegionId.generate();
        FakeCityMunicipalityRepository repository =
                new FakeCityMunicipalityRepository().with(CityMunicipality.register("Medellín", "MED", null, regionId));
        RegisterCityMunicipalityUseCase useCase = new RegisterCityMunicipalityUseCase(repository);

        assertThrows(CityMunicipalityAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterCityMunicipalityCommand("Otra", " MED ", null, regionId)));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterCityMunicipalityUseCase useCase = new RegisterCityMunicipalityUseCase(new FakeCityMunicipalityRepository());

        assertThrows(InvalidCityMunicipalityException.class,
                () -> useCase.execute(new RegisterCityMunicipalityCommand("  ", "MED", null, StateRegionId.generate())));
    }
}