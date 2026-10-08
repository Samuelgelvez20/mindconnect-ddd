package com.mindconnect.application.chat.chatconversation.usecase;

import com.mindconnect.application.chat.chatconversation.command.UpdateChatConversationCommand;
import com.mindconnect.application.chat.chatconversation.dto.ChatConversationResponse;
import com.mindconnect.application.chat.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversation.port.repository.ChatConversationRepository;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public class UpdateChatConversationUseCase {

    private final ChatConversationRepository conversationRepository;

    public UpdateChatConversationUseCase(ChatConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public ChatConversationResponse execute(UpdateChatConversationCommand command) {

        var conversation = conversationRepository.findById(command.id())
                .orElseThrow(() -> new ChatConversationNotFoundApplicationException(command.id()));

        conversation.update(
                command.conversationStatusId(),
                command.priorityId(),
                command.lastMessageAt(),
                command.closed(),
                command.closedAt());

        return ChatConversationResponse.from(conversationRepository.save(conversation));
    }
}