package com.mindconnect.application.professional.study.usecase;

import com.mindconnect.application.professional.study.command.UpdateStudyCommand;
import com.mindconnect.application.professional.study.dto.StudyResponse;
import com.mindconnect.application.professional.study.exception.StudyNotFoundApplicationException;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;

public class UpdateStudyUseCase {

    private final StudyRepository studyRepository;

    public UpdateStudyUseCase(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public StudyResponse execute(UpdateStudyCommand command) {

        var study = studyRepository.findById(command.id())
                .orElseThrow(() -> new StudyNotFoundApplicationException(command.id()));

        study.update(command.name());

        return StudyResponse.from(studyRepository.save(study));
    }
}