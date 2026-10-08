package com.mindconnect.domain.ai.chatairun;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;

public class FakeChatAiRunRepository implements ChatAiRunRepository {

    private final ConcurrentMap<UUID, ChatAiRun> store = new ConcurrentHashMap<>();

    @Override
    public ChatAiRun save(ChatAiRun chatAiRun) {
        store.put(chatAiRun.id().value(), chatAiRun);
        return chatAiRun;
    }

    @Override
    public Optional<ChatAiRun> findById(ChatAiRunId id) {
        return Optional.ofNullable(store.get(id.value()));
    }

    @Override
    public List<ChatAiRun> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(ChatAiRun chatAiRun) {
        store.remove(chatAiRun.id().value());
    }
}