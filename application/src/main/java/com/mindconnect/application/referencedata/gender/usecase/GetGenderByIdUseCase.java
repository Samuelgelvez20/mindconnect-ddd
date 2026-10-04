package com.mindconnect.application.referencedata.gender.usecase;

import com.mindconnect.application.referencedata.gender.dto.GenderResponse;
import com.mindconnect.application.referencedata.gender.exception.GenderNotFoundApplicationException;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;

public class GetGenderByIdUseCase {

    private final GenderRepository genderRepository;

    public GetGenderByIdUseCase(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public GenderResponse execute(GenderId id) {
        return genderRepository.findById(id)
                .map(GenderResponse::from)
                .orElseThrow(() -> new GenderNotFoundApplicationException(id));
    }
}