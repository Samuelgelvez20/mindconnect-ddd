package com.mindconnect.application.contact.contact.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.contact.contact.model.aggregate.Contact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeContactRepository implements ContactRepository {

    private final Map<ContactId, Contact> store = new LinkedHashMap<>();
    private final java.util.List<Contact> deleted = new ArrayList<>();

    FakeContactRepository with(Contact... contacts) {
        for (Contact contact : contacts) {
            store.put(contact.id(), contact);
        }
        return this;
    }

    java.util.List<Contact> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public Contact save(Contact contact) {
        store.put(contact.id(), contact);
        return contact;
    }

    @Override
    public java.util.Optional<Contact> findById(ContactId id) {
        return java.util.Optional.ofNullable(store.get(id));
    }

    @Override
    public java.util.List<Contact> findAll() {
        return java.util.List.copyOf(store.values());
    }

    @Override
    public void delete(Contact contact) {
        store.remove(contact.id());
        deleted.add(contact);
    }
}