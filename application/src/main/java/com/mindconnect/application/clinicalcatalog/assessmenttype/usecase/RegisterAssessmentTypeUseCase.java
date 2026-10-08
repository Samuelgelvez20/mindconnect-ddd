package com.mindconnect.application.clinicalcatalog.assessmenttype.usecase;

import com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;
import com.mindconnect.application.clinicalcatalog.assessmenttype.command.RegisterAssessmentTypeCommand;
import com.mindconnect.application.clinicalcatalog.assessmenttype.exception.AssessmentTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;

import java.util.Optional;

public class RegisterAssessmentTypeUseCase {

    private final AssessmentTypeRepository repository;

    public RegisterAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        this.repository = repository;
    }

    public AssessmentTypeResponse execute(RegisterAssessmentTypeCommand command) {
        Optional<AssessmentType> existing = repository.findByCode(command.code());

        if (existing.isPresent()) {
            throw new AssessmentTypeAlreadyExistsApplicationException(command.code());
        }

        AssessmentType type = AssessmentType.register(command.code(), command.name(), command.description());
        repository.save(type);

        return AssessmentTypeResponse.from(type);
    }
}