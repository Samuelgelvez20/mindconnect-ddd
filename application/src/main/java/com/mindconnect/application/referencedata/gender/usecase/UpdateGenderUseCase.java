package com.mindconnect.application.referencedata.gender.usecase;

import com.mindconnect.application.referencedata.gender.command.UpdateGenderCommand;
import com.mindconnect.application.referencedata.gender.dto.GenderResponse;
import com.mindconnect.application.referencedata.gender.exception.GenderAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.gender.exception.GenderNotFoundApplicationException;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;

public class UpdateGenderUseCase {

    private final GenderRepository genderRepository;

    public UpdateGenderUseCase(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public GenderResponse execute(UpdateGenderCommand command) {

        var gender = genderRepository.findById(command.id())
                .orElseThrow(() -> new GenderNotFoundApplicationException(command.id()));

        gender.update(command.description());

        if (genderRepository.existsByDescriptionAndIdNot(gender.description(), gender.id())) {
            throw new GenderAlreadyExistsApplicationException(gender.description());
        }

        return GenderResponse.from(genderRepository.save(gender));
    }
}