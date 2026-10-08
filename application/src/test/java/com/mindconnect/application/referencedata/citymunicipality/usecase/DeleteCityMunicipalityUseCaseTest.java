package com.mindconnect.application.referencedata.citymunicipality.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.mindconnect.domain.referencedata.citymunicipality.event.CityMunicipalityDeletedEvent;
import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

class DeleteCityMunicipalityUseCaseTest {

    @Test
    void shouldDeleteExistingCityMunicipalityAndRecordEvent() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("Medellín", "MED", null, regionId);
        FakeCityMunicipalityRepository repository = new FakeCityMunicipalityRepository().with(cityMunicipality);
        DeleteCityMunicipalityUseCase useCase = new DeleteCityMunicipalityUseCase(repository);

        useCase.execute(cityMunicipality.id());

        assertEquals(1, repository.deleted().size());
        assertSame(cityMunicipality, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(CityMunicipalityDeletedEvent.class, cityMunicipality.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenCityMunicipalityDoesNotExist() {
        FakeCityMunicipalityRepository repository = new FakeCityMunicipalityRepository();
        DeleteCityMunicipalityUseCase useCase = new DeleteCityMunicipalityUseCase(repository);

        assertThrows(CityMunicipalityNotFoundApplicationException.class,
                () -> useCase.execute(CityMunicipalityId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}