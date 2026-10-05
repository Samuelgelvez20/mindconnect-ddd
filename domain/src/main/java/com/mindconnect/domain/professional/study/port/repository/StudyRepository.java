package com.mindconnect.domain.professional.study.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.study.model.aggregate.Study;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;

public interface StudyRepository {

    Study save(Study study);

    Optional<Study> findById(StudyId id);

    List<Study> findAll();

    void delete(Study study);
}