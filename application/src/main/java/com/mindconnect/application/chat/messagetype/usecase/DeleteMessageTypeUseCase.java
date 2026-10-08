package com.mindconnect.application.chat.messagetype.usecase;

import com.mindconnect.application.chat.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.messagetype.port.repository.MessageTypeRepository;

public class DeleteMessageTypeUseCase {

    private final MessageTypeRepository messageTypeRepository;

    public DeleteMessageTypeUseCase(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    public void execute(MessageTypeId id) {

        var messageType = messageTypeRepository.findById(id)
                .orElseThrow(() -> new MessageTypeNotFoundApplicationException(id));

        messageType.delete();
        messageTypeRepository.delete(messageType);
    }
}