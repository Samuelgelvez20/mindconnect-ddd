package com.mindconnect.domain.contact.patientcontact.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.patientcontact.model.aggregate.PatientContact;
import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;

public interface PatientContactRepository {

    PatientContact save(PatientContact patientContact);

    Optional<PatientContact> findById(PatientContactId id);

    List<PatientContact> findAll();

    void delete(PatientContact patientContact);
}