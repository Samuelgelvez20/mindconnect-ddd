package com.mindconnect.domain.chat.chatescalationstatushistory.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;

public interface ChatEscalationStatusHistoryRepository {

    ChatEscalationStatusHistory save(ChatEscalationStatusHistory chatEscalationStatusHistory);

    Optional<ChatEscalationStatusHistory> findById(ChatEscalationStatusHistoryId id);

    List<ChatEscalationStatusHistory> findAll();

    void delete(ChatEscalationStatusHistory chatEscalationStatusHistory);
}