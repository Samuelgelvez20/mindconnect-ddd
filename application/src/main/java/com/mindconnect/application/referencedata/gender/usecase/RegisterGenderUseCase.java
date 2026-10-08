package com.mindconnect.application.referencedata.gender.usecase;

import com.mindconnect.application.referencedata.gender.command.RegisterGenderCommand;
import com.mindconnect.application.referencedata.gender.dto.GenderResponse;
import com.mindconnect.application.referencedata.gender.exception.GenderAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;

public class RegisterGenderUseCase {

    private final GenderRepository genderRepository;

    public RegisterGenderUseCase(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public GenderResponse execute(RegisterGenderCommand command) {

        Gender gender = Gender.register(command.description());

        if (genderRepository.existsByDescription(gender.description())) {
            throw new GenderAlreadyExistsApplicationException(gender.description());
        }

        return GenderResponse.from(genderRepository.save(gender));
    }
}