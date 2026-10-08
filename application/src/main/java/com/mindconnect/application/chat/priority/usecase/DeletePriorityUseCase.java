package com.mindconnect.application.chat.priority.usecase;

import com.mindconnect.application.chat.priority.exception.PriorityNotFoundApplicationException;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.domain.chat.priority.port.repository.PriorityRepository;

public class DeletePriorityUseCase {

    private final PriorityRepository priorityRepository;

    public DeletePriorityUseCase(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public void execute(PriorityId id) {

        var priority = priorityRepository.findById(id)
                .orElseThrow(() -> new PriorityNotFoundApplicationException(id));

        priority.delete();
        priorityRepository.delete(priority);
    }
}