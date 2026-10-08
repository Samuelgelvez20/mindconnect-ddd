package com.mindconnect.application.chat.chatmessage.usecase;

import com.mindconnect.application.chat.chatmessage.dto.ChatMessageResponse;
import com.mindconnect.application.chat.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.chat.chatmessage.port.repository.ChatMessageRepository;

public class GetChatMessageByIdUseCase {

    private final ChatMessageRepository messageRepository;

    public GetChatMessageByIdUseCase(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ChatMessageResponse execute(ChatMessageId id) {
        return messageRepository.findById(id)
                .map(ChatMessageResponse::from)
                .orElseThrow(() -> new ChatMessageNotFoundApplicationException(id));
    }
}