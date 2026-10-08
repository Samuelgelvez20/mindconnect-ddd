package com.mindconnect.application.chat.priority.usecase;

import com.mindconnect.application.chat.priority.command.UpdatePriorityCommand;
import com.mindconnect.application.chat.priority.dto.PriorityResponse;
import com.mindconnect.application.chat.priority.exception.PriorityAlreadyExistsApplicationException;
import com.mindconnect.application.chat.priority.exception.PriorityNotFoundApplicationException;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.domain.chat.priority.port.repository.PriorityRepository;

public class UpdatePriorityUseCase {

    private final PriorityRepository priorityRepository;

    public UpdatePriorityUseCase(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public PriorityResponse execute(UpdatePriorityCommand command) {

        var priority = priorityRepository.findById(command.id())
                .orElseThrow(() -> new PriorityNotFoundApplicationException(command.id()));

        priority.update(command.name());

        if (priorityRepository.existsByNameAndIdNot(priority.name(), priority.id())) {
            throw new PriorityAlreadyExistsApplicationException(priority.name());
        }

        return PriorityResponse.from(priorityRepository.save(priority));
    }
}