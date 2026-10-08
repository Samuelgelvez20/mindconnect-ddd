package com.mindconnect.domain.professional.professionalstudy.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;

public interface ProfessionalStudyRepository {

    ProfessionalStudy save(ProfessionalStudy professionalStudy);

    Optional<ProfessionalStudy> findById(ProfessionalStudyId id);

    List<ProfessionalStudy> findAll();

    void delete(ProfessionalStudy professionalStudy);
}