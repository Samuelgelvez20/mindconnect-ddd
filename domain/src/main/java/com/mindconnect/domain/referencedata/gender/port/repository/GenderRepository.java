package com.mindconnect.domain.referencedata.gender.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public interface GenderRepository {

    Gender save(Gender gender);

    Optional<Gender> findById(GenderId id);

    List<Gender> findAll();

    boolean existsByDescription(String description);

    boolean existsByDescriptionAndIdNot(String description, GenderId id);

    void delete(Gender gender);
}