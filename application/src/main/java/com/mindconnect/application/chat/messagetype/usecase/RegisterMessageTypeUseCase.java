package com.mindconnect.application.chat.messagetype.usecase;

import com.mindconnect.application.chat.messagetype.command.RegisterMessageTypeCommand;
import com.mindconnect.application.chat.messagetype.dto.MessageTypeResponse;
import com.mindconnect.application.chat.messagetype.exception.MessageTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.chat.messagetype.model.aggregate.MessageType;
import com.mindconnect.domain.chat.messagetype.port.repository.MessageTypeRepository;

public class RegisterMessageTypeUseCase {

    private final MessageTypeRepository messageTypeRepository;

    public RegisterMessageTypeUseCase(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    public MessageTypeResponse execute(RegisterMessageTypeCommand command) {

        MessageType messageType = MessageType.register(command.name());

        if (messageTypeRepository.existsByName(messageType.name())) {
            throw new MessageTypeAlreadyExistsApplicationException(messageType.name());
        }

        return MessageTypeResponse.from(messageTypeRepository.save(messageType));
    }
}