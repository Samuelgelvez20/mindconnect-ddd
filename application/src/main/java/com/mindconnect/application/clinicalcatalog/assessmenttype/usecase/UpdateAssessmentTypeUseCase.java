package com.mindconnect.application.clinicalcatalog.assessmenttype.usecase;

import com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;
import com.mindconnect.application.clinicalcatalog.assessmenttype.command.UpdateAssessmentTypeCommand;
import com.mindconnect.application.clinicalcatalog.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.assessmenttype.exception.AssessmentTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;

public class UpdateAssessmentTypeUseCase {

    private final AssessmentTypeRepository repository;

    public UpdateAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        this.repository = repository;
    }

    public AssessmentTypeResponse execute(AssessmentTypeId id, UpdateAssessmentTypeCommand command) {
        AssessmentType type = repository.findById(id)
                .orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id.value().toString()));

        if (repository.existsByCodeAndIdNot(command.code(), id)) {
            throw new AssessmentTypeAlreadyExistsApplicationException(command.code());
        }

        type.update(command.code(), command.name(), command.description(), command.active());

        repository.save(type);

        return AssessmentTypeResponse.from(type);
    }
}