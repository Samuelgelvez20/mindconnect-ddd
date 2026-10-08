package com.mindconnect.domain.clinicalcatalog.consenttype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;

public interface ConsentTypeRepository {

    ConsentType save(ConsentType consentType);

    Optional<ConsentType> findById(ConsentTypeId id);

    Optional<ConsentType> findByCode(String code);

    boolean existsByCodeAndIdNot(String code, ConsentTypeId id);

    List<ConsentType> findAll();

    void delete(ConsentType consentType);
}