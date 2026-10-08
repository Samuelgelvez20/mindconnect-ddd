package com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.sendertype.model.aggregate.SenderType;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.chat.sendertype.port.repository.SenderTypeRepository;
import com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.entity.SenderTypeJpaEntity;
import com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.mappers.SenderTypePersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SenderTypeRepositoryAdapter implements SenderTypeRepository {

    private final SenderTypeJpaRepository jpaRepository;
    private final SenderTypePersistenceMapper mapper;

    public SenderTypeRepositoryAdapter(SenderTypeJpaRepository jpaRepository, SenderTypePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public SenderType save(SenderType senderType) {
        SenderTypeJpaEntity entity = mapper.toJpa(senderType);
        SenderTypeJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<SenderType> findById(SenderTypeId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<SenderType> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(SenderType senderType) {
        jpaRepository.deleteById(senderType.id().value());
    }

    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    public boolean existsByNameAndIdNot(String name, SenderTypeId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public Optional<SenderType> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }
}