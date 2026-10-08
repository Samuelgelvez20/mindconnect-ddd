package com.mindconnect.domain.ai.aiprovider.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

public interface AiProviderRepository {

    AiProvider save(AiProvider aiProvider);

    Optional<AiProvider> findById(AiProviderId id);

    List<AiProvider> findAll();

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, AiProviderId id);

    void delete(AiProvider aiProvider);
}