package com.mindconnect.domain.ai.chatairunerror;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;

public class FakeChatAiRunErrorRepository implements ChatAiRunErrorRepository {

    private final ConcurrentMap<UUID, ChatAiRunError> store = new ConcurrentHashMap<>();

    @Override
    public ChatAiRunError save(ChatAiRunError chatAiRunError) {
        store.put(chatAiRunError.id().value(), chatAiRunError);
        return chatAiRunError;
    }

    @Override
    public Optional<ChatAiRunError> findById(ChatAiRunErrorId id) {
        return Optional.ofNullable(store.get(id.value()));
    }

    @Override
    public List<ChatAiRunError> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(ChatAiRunError chatAiRunError) {
        store.remove(chatAiRunError.id().value());
    }
}