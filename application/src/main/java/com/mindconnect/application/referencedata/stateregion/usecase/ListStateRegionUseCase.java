package com.mindconnect.application.referencedata.stateregion.usecase;

import java.util.List;

import com.mindconnect.application.referencedata.stateregion.dto.StateRegionResponse;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;

public class ListStateRegionUseCase {

    private final StateRegionRepository stateRegionRepository;

    public ListStateRegionUseCase(StateRegionRepository stateRegionRepository) {
        this.stateRegionRepository = stateRegionRepository;
    }

    public List<StateRegionResponse> execute() {
        return stateRegionRepository.findAll()
                .stream()
                .map(StateRegionResponse::from)
                .toList();
    }
}