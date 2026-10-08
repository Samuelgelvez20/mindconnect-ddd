package com.mindconnect.domain.ai.chatairunerror.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

public interface ChatAiRunErrorRepository {

    ChatAiRunError save(ChatAiRunError chatAiRunError);

    Optional<ChatAiRunError> findById(ChatAiRunErrorId id);

    List<ChatAiRunError> findAll();

    void delete(ChatAiRunError chatAiRunError);
}