package com.mindconnect.domain.professional.professionaltype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public interface ProfessionalTypeRepository {

    ProfessionalType save(ProfessionalType professionalType);

    Optional<ProfessionalType> findById(ProfessionalTypeId id);

    List<ProfessionalType> findAll();

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, ProfessionalTypeId id);

    void delete(ProfessionalType professionalType);
}