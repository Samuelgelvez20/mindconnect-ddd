package com.mindconnect.infrastructure.professional.professionalstudy.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.mindconnect.infrastructure.professional.professionalstudy.adapters.out.persistence.mappers.ProfessionalStudyPersistenceMapper;

public class ProfessionalStudyRepositoryAdapter implements ProfessionalStudyRepository {

    private final ProfessionalStudyJpaRepository professionalStudyJpaRepository;
    private final ProfessionalStudyPersistenceMapper mapper;

    public ProfessionalStudyRepositoryAdapter(
            ProfessionalStudyJpaRepository professionalStudyJpaRepository,
            ProfessionalStudyPersistenceMapper mapper) {
        this.professionalStudyJpaRepository = professionalStudyJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ProfessionalStudy save(ProfessionalStudy professionalStudy) {
        return mapper.toDomain(professionalStudyJpaRepository.save(mapper.toJpa(professionalStudy)));
    }

    @Override
    public Optional<ProfessionalStudy> findById(ProfessionalStudyId id) {
        return professionalStudyJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ProfessionalStudy> findAll() {
        return professionalStudyJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(ProfessionalStudy professionalStudy) {
        professionalStudyJpaRepository.deleteById(professionalStudy.id().value());
    }
}