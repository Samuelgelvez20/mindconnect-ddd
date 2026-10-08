package com.mindconnect.domain.ai.chatairunstatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.ai.chatairunstatus.port.repository.ChatAiRunStatusRepository;

public class FakeChatAiRunStatusRepository implements ChatAiRunStatusRepository {

    private final ConcurrentMap<UUID, ChatAiRunStatus> store = new ConcurrentHashMap<>();

    @Override
    public ChatAiRunStatus save(ChatAiRunStatus chatAiRunStatus) {
        store.put(chatAiRunStatus.id().value(), chatAiRunStatus);
        return chatAiRunStatus;
    }

    @Override
    public Optional<ChatAiRunStatus> findById(ChatAiRunStatusId id) {
        return Optional.ofNullable(store.get(id.value()));
    }

    @Override
    public Optional<ChatAiRunStatus> findByName(String name) {
        return store.values().stream()
                .filter(s -> s.name().equals(name))
                .findFirst();
    }

    @Override
    public List<ChatAiRunStatus> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByName(String name) {
        return store.values().stream()
                .anyMatch(s -> s.name().equals(name));
    }

    @Override
    public boolean existsByNameAndIdNot(String name, ChatAiRunStatusId id) {
        return store.values().stream()
                .anyMatch(s -> s.name().equals(name) && !s.id().equals(id));
    }

    @Override
    public void delete(ChatAiRunStatus chatAiRunStatus) {
        store.remove(chatAiRunStatus.id().value());
    }
}