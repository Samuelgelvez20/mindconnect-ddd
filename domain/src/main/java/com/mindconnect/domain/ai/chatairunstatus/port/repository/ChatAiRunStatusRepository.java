package com.mindconnect.domain.ai.chatairunstatus.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

public interface ChatAiRunStatusRepository {

    ChatAiRunStatus save(ChatAiRunStatus chatAiRunStatus);

    Optional<ChatAiRunStatus> findById(ChatAiRunStatusId id);

    Optional<ChatAiRunStatus> findByName(String name);

    List<ChatAiRunStatus> findAll();

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, ChatAiRunStatusId id);

    void delete(ChatAiRunStatus chatAiRunStatus);
}