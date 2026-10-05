package com.mindconnect.application.professional.study.usecase;

import com.mindconnect.application.professional.study.dto.StudyResponse;
import com.mindconnect.application.professional.study.exception.StudyNotFoundApplicationException;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;

public class GetStudyByIdUseCase {

    private final StudyRepository studyRepository;

    public GetStudyByIdUseCase(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public StudyResponse execute(StudyId id) {
        return studyRepository.findById(id)
                .map(StudyResponse::from)
                .orElseThrow(() -> new StudyNotFoundApplicationException(id));
    }
}