package com.mindconnect.application.professional.study.usecase;

import com.mindconnect.application.professional.study.command.RegisterStudyCommand;
import com.mindconnect.application.professional.study.dto.StudyResponse;
import com.mindconnect.domain.professional.study.model.aggregate.Study;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;

public class RegisterStudyUseCase {

    private final StudyRepository studyRepository;

    public RegisterStudyUseCase(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public StudyResponse execute(RegisterStudyCommand command) {

        Study study = Study.register(command.name());

        return StudyResponse.from(studyRepository.save(study));
    }
}