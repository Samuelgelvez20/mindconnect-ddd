package com.mindconnect.domain.contact.phonecontact.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.phonecontact.model.aggregate.PhoneContact;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;

public interface PhoneContactRepository {

    PhoneContact save(PhoneContact phoneContact);

    Optional<PhoneContact> findById(PhoneContactId id);

    List<PhoneContact> findAll();

    void delete(PhoneContact phoneContact);
}