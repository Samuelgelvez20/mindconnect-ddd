package com.mindconnect.domain.ai.chatairun.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

public interface ChatAiRunRepository {

    ChatAiRun save(ChatAiRun chatAiRun);

    Optional<ChatAiRun> findById(ChatAiRunId id);

    List<ChatAiRun> findAll();

    void delete(ChatAiRun chatAiRun);
}