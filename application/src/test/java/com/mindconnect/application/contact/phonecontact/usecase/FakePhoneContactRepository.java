package com.mindconnect.application.contact.phonecontact.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.contact.phonecontact.model.aggregate.PhoneContact;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;
import com.mindconnect.domain.contact.phonecontact.port.repository.PhoneContactRepository;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakePhoneContactRepository implements PhoneContactRepository {

    private final Map<PhoneContactId, PhoneContact> store = new LinkedHashMap<>();
    private final List<PhoneContact> deleted = new ArrayList<>();

    FakePhoneContactRepository with(PhoneContact... phoneContacts) {
        for (PhoneContact phoneContact : phoneContacts) {
            store.put(phoneContact.id(), phoneContact);
        }
        return this;
    }

    List<PhoneContact> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public PhoneContact save(PhoneContact phoneContact) {
        store.put(phoneContact.id(), phoneContact);
        return phoneContact;
    }

    @Override
    public Optional<PhoneContact> findById(PhoneContactId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<PhoneContact> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void delete(PhoneContact phoneContact) {
        store.remove(phoneContact.id());
        deleted.add(phoneContact);
    }
}