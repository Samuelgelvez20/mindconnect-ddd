package com.mindconnect.application.referencedata.stateregion.usecase;

import com.mindconnect.application.referencedata.stateregion.command.RegisterStateRegionCommand;
import com.mindconnect.application.referencedata.stateregion.dto.StateRegionResponse;
import com.mindconnect.application.referencedata.stateregion.exception.StateRegionAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;

public class RegisterStateRegionUseCase {

    private final StateRegionRepository stateRegionRepository;

    public RegisterStateRegionUseCase(StateRegionRepository stateRegionRepository) {
        this.stateRegionRepository = stateRegionRepository;
    }

    public StateRegionResponse execute(RegisterStateRegionCommand command) {

        StateRegion stateRegion = StateRegion.register(
                command.name(),
                command.code(),
                command.description(),
                command.countryId());

        if (stateRegionRepository.existsByCountryIdAndCode(stateRegion.countryId(), stateRegion.code())) {
            throw new StateRegionAlreadyExistsApplicationException(stateRegion.countryId(), stateRegion.code());
        }

        return StateRegionResponse.from(stateRegionRepository.save(stateRegion));
    }
}