package com.mindconnect.application.clinicalcatalog.consenttype.usecase;

import com.mindconnect.application.clinicalcatalog.consenttype.dto.ConsentTypeResponse;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.port.repository.ConsentTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListConsentTypeUseCase {

    private final ConsentTypeRepository repository;

    public ListConsentTypeUseCase(ConsentTypeRepository repository) {
        this.repository = repository;
    }

    public List<ConsentTypeResponse> execute() {
        return repository.findAll().stream()
                .map(ConsentTypeResponse::from)
                .collect(Collectors.toList());
    }
}