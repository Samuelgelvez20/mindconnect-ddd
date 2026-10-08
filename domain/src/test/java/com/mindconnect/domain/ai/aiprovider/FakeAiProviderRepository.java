package com.mindconnect.domain.ai.aiprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;

public class FakeAiProviderRepository implements AiProviderRepository {

    private final ConcurrentMap<UUID, AiProvider> store = new ConcurrentHashMap<>();

    @Override
    public AiProvider save(AiProvider aiProvider) {
        store.put(aiProvider.id().value(), aiProvider);
        return aiProvider;
    }

    @Override
    public Optional<AiProvider> findById(AiProviderId id) {
        return Optional.ofNullable(store.get(id.value()));
    }

    @Override
    public List<AiProvider> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByName(String name) {
        return store.values().stream()
                .anyMatch(p -> p.name().equals(name));
    }

    @Override
    public boolean existsByNameAndIdNot(String name, AiProviderId id) {
        return store.values().stream()
                .anyMatch(p -> p.name().equals(name) && !p.id().equals(id));
    }

    @Override
    public void delete(AiProvider aiProvider) {
        store.remove(aiProvider.id().value());
    }
}