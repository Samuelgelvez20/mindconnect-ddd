package com.mindconnect.application.referencedata.stateregion.usecase;

import com.mindconnect.application.referencedata.stateregion.exception.StateRegionNotFoundApplicationException;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;

public class DeleteStateRegionUseCase {

    private final StateRegionRepository stateRegionRepository;

    public DeleteStateRegionUseCase(StateRegionRepository stateRegionRepository) {
        this.stateRegionRepository = stateRegionRepository;
    }

    public void execute(StateRegionId id) {

        var stateRegion = stateRegionRepository.findById(id)
                .orElseThrow(() -> new StateRegionNotFoundApplicationException(id));

        stateRegion.delete();
        stateRegionRepository.delete(stateRegion);
    }
}