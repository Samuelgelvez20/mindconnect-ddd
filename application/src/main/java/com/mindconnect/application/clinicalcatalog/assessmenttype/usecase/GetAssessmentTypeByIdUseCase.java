package com.mindconnect.application.clinicalcatalog.assessmenttype.usecase;

import com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;
import com.mindconnect.application.clinicalcatalog.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;

public class GetAssessmentTypeByIdUseCase {

    private final AssessmentTypeRepository repository;

    public GetAssessmentTypeByIdUseCase(AssessmentTypeRepository repository) {
        this.repository = repository;
    }

    public AssessmentTypeResponse execute(AssessmentTypeId id) {
        AssessmentType type = repository.findById(id)
                .orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id.value().toString()));

        return AssessmentTypeResponse.from(type);
    }
}