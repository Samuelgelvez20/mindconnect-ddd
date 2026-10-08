package com.mindconnect.domain.chat.messagetype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.messagetype.model.aggregate.MessageType;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;

public interface MessageTypeRepository {

    MessageType save(MessageType messageType);

    Optional<MessageType> findById(MessageTypeId id);

    Optional<MessageType> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, MessageTypeId id);

    List<MessageType> findAll();

    void delete(MessageType messageType);
}