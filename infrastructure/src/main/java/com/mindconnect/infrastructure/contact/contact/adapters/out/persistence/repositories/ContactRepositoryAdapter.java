package com.mindconnect.infrastructure.contact.contact.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.contact.model.aggregate.Contact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;
import com.mindconnect.infrastructure.contact.contact.adapters.out.persistence.mappers.ContactPersistenceMapper;

public class ContactRepositoryAdapter implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;
    private final ContactPersistenceMapper mapper;

    public ContactRepositoryAdapter(
            ContactJpaRepository contactJpaRepository,
            ContactPersistenceMapper mapper) {
        this.contactJpaRepository = contactJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Contact save(Contact contact) {
        return mapper.toDomain(contactJpaRepository.save(mapper.toJpa(contact)));
    }

    @Override
    public Optional<Contact> findById(ContactId id) {
        return contactJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Contact> findAll() {
        return contactJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Contact contact) {
        contactJpaRepository.deleteById(contact.id().value());
    }
}