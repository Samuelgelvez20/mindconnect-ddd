package com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;

public interface AssessmentTypeRepository {

    AssessmentType save(AssessmentType assessmentType);

    Optional<AssessmentType> findById(AssessmentTypeId id);

    Optional<AssessmentType> findByCode(String code);

    boolean existsByCodeAndIdNot(String code, AssessmentTypeId id);

    List<AssessmentType> findAll();

    void delete(AssessmentType assessmentType);
}