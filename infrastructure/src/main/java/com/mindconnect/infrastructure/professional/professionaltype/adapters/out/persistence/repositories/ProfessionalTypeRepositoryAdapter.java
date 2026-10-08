package com.mindconnect.infrastructure.professional.professionaltype.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;
import com.mindconnect.infrastructure.professional.professionaltype.adapters.out.persistence.mappers.ProfessionalTypePersistenceMapper;

public class ProfessionalTypeRepositoryAdapter implements ProfessionalTypeRepository {

    private final ProfessionalTypeJpaRepository professionalTypeJpaRepository;
    private final ProfessionalTypePersistenceMapper mapper;

    public ProfessionalTypeRepositoryAdapter(
            ProfessionalTypeJpaRepository professionalTypeJpaRepository,
            ProfessionalTypePersistenceMapper mapper) {
        this.professionalTypeJpaRepository = professionalTypeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ProfessionalType save(ProfessionalType professionalType) {
        return mapper.toDomain(professionalTypeJpaRepository.save(mapper.toJpa(professionalType)));
    }

    @Override
    public Optional<ProfessionalType> findById(ProfessionalTypeId id) {
        return professionalTypeJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ProfessionalType> findAll() {
        return professionalTypeJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByName(String name) {
        return professionalTypeJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, ProfessionalTypeId id) {
        return professionalTypeJpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public void delete(ProfessionalType professionalType) {
        professionalTypeJpaRepository.deleteById(professionalType.id().value());
    }
}