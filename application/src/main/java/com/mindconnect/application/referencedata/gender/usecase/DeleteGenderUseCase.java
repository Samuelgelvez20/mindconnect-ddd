package com.mindconnect.application.referencedata.gender.usecase;

import com.mindconnect.application.referencedata.gender.exception.GenderNotFoundApplicationException;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;

public class DeleteGenderUseCase {

    private final GenderRepository genderRepository;

    public DeleteGenderUseCase(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public void execute(GenderId id) {

        var gender = genderRepository.findById(id)
                .orElseThrow(() -> new GenderNotFoundApplicationException(id));

        gender.delete();
        genderRepository.delete(gender);
    }
}