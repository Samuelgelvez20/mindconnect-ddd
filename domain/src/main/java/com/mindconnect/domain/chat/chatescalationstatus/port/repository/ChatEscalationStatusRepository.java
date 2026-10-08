package com.mindconnect.domain.chat.chatescalationstatus.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.chatescalationstatus.model.aggregate.ChatEscalationStatus;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public interface ChatEscalationStatusRepository {

    ChatEscalationStatus save(ChatEscalationStatus chatEscalationStatus);

    Optional<ChatEscalationStatus> findById(ChatEscalationStatusId id);

    Optional<ChatEscalationStatus> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, ChatEscalationStatusId id);

    List<ChatEscalationStatus> findAll();

    void delete(ChatEscalationStatus chatEscalationStatus);
}