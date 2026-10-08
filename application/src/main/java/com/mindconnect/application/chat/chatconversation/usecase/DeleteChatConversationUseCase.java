package com.mindconnect.application.chat.chatconversation.usecase;

import com.mindconnect.application.chat.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversation.port.repository.ChatConversationRepository;

public class DeleteChatConversationUseCase {

    private final ChatConversationRepository conversationRepository;

    public DeleteChatConversationUseCase(ChatConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public void execute(ChatConversationId id) {

        var conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new ChatConversationNotFoundApplicationException(id));

        conversation.delete();
        conversationRepository.delete(conversation);
    }
}