package com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;
import com.mindconnect.domain.clinicalcatalog.consenttype.port.repository.ConsentTypeRepository;
import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.mappers.ConsentTypePersistenceMapper;

public class ConsentTypeRepositoryAdapter implements ConsentTypeRepository {

    private final ConsentTypeJpaRepository jpaRepository;
    private final ConsentTypePersistenceMapper mapper;

    public ConsentTypeRepositoryAdapter(
            ConsentTypeJpaRepository jpaRepository,
            ConsentTypePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ConsentType save(ConsentType consentType) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(consentType)));
    }

    @Override
    public Optional<ConsentType> findById(ConsentTypeId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<ConsentType> findByCode(String code) {
        return jpaRepository.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<ConsentType> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, ConsentTypeId id) {
        return jpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(ConsentType consentType) {
        jpaRepository.deleteById(consentType.id().value());
    }
}