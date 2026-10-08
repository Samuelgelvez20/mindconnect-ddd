package com.mindconnect.domain.contact.emailcontact.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;

public interface EmailContactRepository {

    EmailContact save(EmailContact emailContact);

    Optional<EmailContact> findById(EmailContactId id);

    List<EmailContact> findAll();

    void delete(EmailContact emailContact);
}