package com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;
import com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.mappers.EmailContactPersistenceMapper;

public class EmailContactRepositoryAdapter implements EmailContactRepository {

    private final EmailContactJpaRepository jpaRepository;
    private final EmailContactPersistenceMapper mapper;

    public EmailContactRepositoryAdapter(
            EmailContactJpaRepository jpaRepository,
            EmailContactPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public EmailContact save(EmailContact emailContact) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(emailContact)));
    }

    @Override
    public Optional<EmailContact> findById(EmailContactId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<EmailContact> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(EmailContact emailContact) {
        jpaRepository.deleteById(emailContact.id().value());
    }
}