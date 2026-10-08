package com.mindconnect.application.referencedata.stateregion.usecase;

import com.mindconnect.application.referencedata.stateregion.dto.StateRegionResponse;
import com.mindconnect.application.referencedata.stateregion.exception.StateRegionNotFoundApplicationException;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;

public class GetStateRegionByIdUseCase {

    private final StateRegionRepository stateRegionRepository;

    public GetStateRegionByIdUseCase(StateRegionRepository stateRegionRepository) {
        this.stateRegionRepository = stateRegionRepository;
    }

    public StateRegionResponse execute(StateRegionId id) {
        return stateRegionRepository.findById(id)
                .map(StateRegionResponse::from)
                .orElseThrow(() -> new StateRegionNotFoundApplicationException(id));
    }
}