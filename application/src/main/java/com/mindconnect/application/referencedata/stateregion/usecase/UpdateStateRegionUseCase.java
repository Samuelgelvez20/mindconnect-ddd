package com.mindconnect.application.referencedata.stateregion.usecase;

import com.mindconnect.application.referencedata.stateregion.command.UpdateStateRegionCommand;
import com.mindconnect.application.referencedata.stateregion.dto.StateRegionResponse;
import com.mindconnect.application.referencedata.stateregion.exception.StateRegionAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.stateregion.exception.StateRegionNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;

public class UpdateStateRegionUseCase {

    private final StateRegionRepository stateRegionRepository;

    public UpdateStateRegionUseCase(StateRegionRepository stateRegionRepository) {
        this.stateRegionRepository = stateRegionRepository;
    }

    public StateRegionResponse execute(UpdateStateRegionCommand command) {

        var stateRegion = stateRegionRepository.findById(command.id())
                .orElseThrow(() -> new StateRegionNotFoundApplicationException(command.id()));

        stateRegion.update(command.name(), command.code(), command.description(), command.active());

        if (stateRegionRepository.existsByCountryIdAndCodeAndIdNot(stateRegion.countryId(), stateRegion.code(), stateRegion.id())) {
            throw new StateRegionAlreadyExistsApplicationException(stateRegion.countryId(), stateRegion.code());
        }

        return StateRegionResponse.from(stateRegionRepository.save(stateRegion));
    }
}