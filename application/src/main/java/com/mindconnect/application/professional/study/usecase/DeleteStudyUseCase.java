package com.mindconnect.application.professional.study.usecase;

import com.mindconnect.application.professional.study.exception.StudyNotFoundApplicationException;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;

public class DeleteStudyUseCase {

    private final StudyRepository studyRepository;

    public DeleteStudyUseCase(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public void execute(StudyId id) {

        var study = studyRepository.findById(id)
                .orElseThrow(() -> new StudyNotFoundApplicationException(id));

        study.delete();
        studyRepository.delete(study);
    }
}