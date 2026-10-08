package com.mindconnect.application.chat.priority.usecase;

import com.mindconnect.application.chat.priority.dto.PriorityResponse;
import com.mindconnect.application.chat.priority.exception.PriorityNotFoundApplicationException;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.domain.chat.priority.port.repository.PriorityRepository;

public class GetPriorityByIdUseCase {

    private final PriorityRepository priorityRepository;

    public GetPriorityByIdUseCase(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public PriorityResponse execute(PriorityId id) {
        return priorityRepository.findById(id)
                .map(PriorityResponse::from)
                .orElseThrow(() -> new PriorityNotFoundApplicationException(id));
    }
}