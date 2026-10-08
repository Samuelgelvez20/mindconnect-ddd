package com.mindconnect.application.chat.messagetype.usecase;

import com.mindconnect.application.chat.messagetype.command.UpdateMessageTypeCommand;
import com.mindconnect.application.chat.messagetype.dto.MessageTypeResponse;
import com.mindconnect.application.chat.messagetype.exception.MessageTypeAlreadyExistsApplicationException;
import com.mindconnect.application.chat.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.messagetype.port.repository.MessageTypeRepository;

public class UpdateMessageTypeUseCase {

    private final MessageTypeRepository messageTypeRepository;

    public UpdateMessageTypeUseCase(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    public MessageTypeResponse execute(UpdateMessageTypeCommand command) {

        var messageType = messageTypeRepository.findById(command.id())
                .orElseThrow(() -> new MessageTypeNotFoundApplicationException(command.id()));

        messageType.update(command.name());

        if (messageTypeRepository.existsByNameAndIdNot(messageType.name(), messageType.id())) {
            throw new MessageTypeAlreadyExistsApplicationException(messageType.name());
        }

        return MessageTypeResponse.from(messageTypeRepository.save(messageType));
    }
}