package com.mindconnect.application.referencedata.stateregion.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.stateregion.exception.StateRegionNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.event.StateRegionDeletedEvent;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

class DeleteStateRegionUseCaseTest {

    @Test
    void shouldDeleteExistingStateRegionAndRecordEvent() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("Antioquia", "ANT", null, countryId);
        FakeStateRegionRepository repository = new FakeStateRegionRepository().with(stateRegion);
        DeleteStateRegionUseCase useCase = new DeleteStateRegionUseCase(repository);

        useCase.execute(stateRegion.id());

        assertEquals(1, repository.deleted().size());
        assertSame(stateRegion, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(StateRegionDeletedEvent.class, stateRegion.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenStateRegionDoesNotExist() {
        FakeStateRegionRepository repository = new FakeStateRegionRepository();
        DeleteStateRegionUseCase useCase = new DeleteStateRegionUseCase(repository);

        assertThrows(StateRegionNotFoundApplicationException.class,
                () -> useCase.execute(StateRegionId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}