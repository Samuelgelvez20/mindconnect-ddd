package com.mindconnect.application.chat.chatmessage.usecase;

import com.mindconnect.application.chat.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.chat.chatmessage.port.repository.ChatMessageRepository;

public class DeleteChatMessageUseCase {

    private final ChatMessageRepository messageRepository;

    public DeleteChatMessageUseCase(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void execute(ChatMessageId id) {

        var message = messageRepository.findById(id)
                .orElseThrow(() -> new ChatMessageNotFoundApplicationException(id));

        message.delete();
        messageRepository.delete(message);
    }
}