package com.mindconnect.domain.chat.sendertype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.sendertype.model.aggregate.SenderType;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;

public interface SenderTypeRepository {

    SenderType save(SenderType senderType);

    Optional<SenderType> findById(SenderTypeId id);

    Optional<SenderType> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, SenderTypeId id);

    List<SenderType> findAll();

    void delete(SenderType senderType);
}