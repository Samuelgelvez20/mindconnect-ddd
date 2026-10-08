package com.mindconnect.domain.chat.chatescalation.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.chatescalation.model.aggregate.ChatEscalation;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;

public interface ChatEscalationRepository {

    ChatEscalation save(ChatEscalation chatEscalation);

    Optional<ChatEscalation> findById(ChatEscalationId id);

    List<ChatEscalation> findAll();

    void delete(ChatEscalation chatEscalation);
}