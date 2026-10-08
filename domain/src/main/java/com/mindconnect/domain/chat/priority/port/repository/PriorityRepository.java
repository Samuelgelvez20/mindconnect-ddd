package com.mindconnect.domain.chat.priority.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.priority.model.aggregate.Priority;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public interface PriorityRepository {

    Priority save(Priority priority);

    Optional<Priority> findById(PriorityId id);

    Optional<Priority> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, PriorityId id);

    List<Priority> findAll();

    void delete(Priority priority);
}