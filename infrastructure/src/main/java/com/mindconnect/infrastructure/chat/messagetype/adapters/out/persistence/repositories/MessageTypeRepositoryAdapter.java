package com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.messagetype.model.aggregate.MessageType;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.messagetype.port.repository.MessageTypeRepository;
import com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.entity.MessageTypeJpaEntity;
import com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.mappers.MessageTypePersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MessageTypeRepositoryAdapter implements MessageTypeRepository {

    private final MessageTypeJpaRepository jpaRepository;
    private final MessageTypePersistenceMapper mapper;

    public MessageTypeRepositoryAdapter(MessageTypeJpaRepository jpaRepository, MessageTypePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public MessageType save(MessageType messageType) {
        MessageTypeJpaEntity entity = mapper.toJpa(messageType);
        MessageTypeJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<MessageType> findById(MessageTypeId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<MessageType> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(MessageType messageType) {
        jpaRepository.deleteById(messageType.id().value());
    }

    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    public boolean existsByNameAndIdNot(String name, MessageTypeId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public Optional<MessageType> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }
}