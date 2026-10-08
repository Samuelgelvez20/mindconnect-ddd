package com.mindconnect.application.chat.priority.usecase;

import java.util.List;

import com.mindconnect.application.chat.priority.dto.PriorityResponse;
import com.mindconnect.domain.chat.priority.port.repository.PriorityRepository;

public class ListPriorityUseCase {

    private final PriorityRepository priorityRepository;

    public ListPriorityUseCase(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public List<PriorityResponse> execute() {
        return priorityRepository.findAll()
                .stream()
                .map(PriorityResponse::from)
                .toList();
    }
}