package com.mindconnect.application.contact.emailcontact.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeEmailContactRepository implements EmailContactRepository {

    private final Map<EmailContactId, EmailContact> store = new LinkedHashMap<>();
    private final List<EmailContact> deleted = new ArrayList<>();

    FakeEmailContactRepository with(EmailContact... emailContacts) {
        for (EmailContact emailContact : emailContacts) {
            store.put(emailContact.id(), emailContact);
        }
        return this;
    }

    List<EmailContact> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public EmailContact save(EmailContact emailContact) {
        store.put(emailContact.id(), emailContact);
        return emailContact;
    }

    @Override
    public Optional<EmailContact> findById(EmailContactId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<EmailContact> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void delete(EmailContact emailContact) {
        store.remove(emailContact.id());
        deleted.add(emailContact);
    }
}