package com.mindconnect.application.referencedata.gender.usecase;

import java.util.List;

import com.mindconnect.application.referencedata.gender.dto.GenderResponse;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;

public class ListGenderUseCase {

    private final GenderRepository genderRepository;

    public ListGenderUseCase(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public List<GenderResponse> execute() {
        return genderRepository.findAll()
                .stream()
                .map(GenderResponse::from)
                .toList();
    }
}