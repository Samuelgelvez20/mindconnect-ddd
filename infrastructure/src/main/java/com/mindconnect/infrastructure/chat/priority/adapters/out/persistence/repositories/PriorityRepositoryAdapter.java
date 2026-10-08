package com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.priority.model.aggregate.Priority;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.domain.chat.priority.port.repository.PriorityRepository;
import com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.entity.PriorityJpaEntity;
import com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.mappers.PriorityPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PriorityRepositoryAdapter implements PriorityRepository {

    private final PriorityJpaRepository jpaRepository;
    private final PriorityPersistenceMapper mapper;

    public PriorityRepositoryAdapter(PriorityJpaRepository jpaRepository, PriorityPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Priority save(Priority priority) {
        PriorityJpaEntity entity = mapper.toJpa(priority);
        PriorityJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Priority> findById(PriorityId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Priority> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Priority priority) {
        jpaRepository.deleteById(priority.id().value());
    }

    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    public boolean existsByNameAndIdNot(String name, PriorityId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public Optional<Priority> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }
}