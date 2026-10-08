package com.mindconnect.application.chat.messagetype.usecase;

import com.mindconnect.application.chat.messagetype.dto.MessageTypeResponse;
import com.mindconnect.application.chat.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.messagetype.port.repository.MessageTypeRepository;

public class GetMessageTypeByIdUseCase {

    private final MessageTypeRepository messageTypeRepository;

    public GetMessageTypeByIdUseCase(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    public MessageTypeResponse execute(MessageTypeId id) {
        return messageTypeRepository.findById(id)
                .map(MessageTypeResponse::from)
                .orElseThrow(() -> new MessageTypeNotFoundApplicationException(id));
    }
}