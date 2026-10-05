package com.mindconnect.domain.contact.contact.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.contact.model.aggregate.Contact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public interface ContactRepository {

    Contact save(Contact contact);

    Optional<Contact> findById(ContactId id);

    List<Contact> findAll();

    void delete(Contact contact);
}