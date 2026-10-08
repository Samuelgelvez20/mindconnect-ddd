package com.mindconnect.application.clinicalcatalog.assessmenttype.usecase;

import com.mindconnect.application.clinicalcatalog.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;

public class DeleteAssessmentTypeUseCase {

    private final AssessmentTypeRepository repository;

    public DeleteAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        this.repository = repository;
    }

    public void execute(AssessmentTypeId id) {
        AssessmentType type = repository.findById(id)
                .orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id.value().toString()));

        repository.delete(type);
    }
}