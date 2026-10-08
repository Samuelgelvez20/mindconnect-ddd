package com.mindconnect.application.clinicalcatalog.assessmenttype.usecase;

import com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListAssessmentTypeUseCase {

    private final AssessmentTypeRepository repository;

    public ListAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        this.repository = repository;
    }

    public List<AssessmentTypeResponse> execute() {
        return repository.findAll().stream()
                .map(AssessmentTypeResponse::from)
                .collect(Collectors.toList());
    }
}