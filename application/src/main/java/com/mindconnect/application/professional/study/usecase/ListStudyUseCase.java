package com.mindconnect.application.professional.study.usecase;

import java.util.List;

import com.mindconnect.application.professional.study.dto.StudyResponse;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;

public class ListStudyUseCase {

    private final StudyRepository studyRepository;

    public ListStudyUseCase(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public List<StudyResponse> execute() {
        return studyRepository.findAll()
                .stream()
                .map(StudyResponse::from)
                .toList();
    }
}