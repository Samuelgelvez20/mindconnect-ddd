package com.mindconnect.application.ai.chatairun.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

public record ChatAiRunResponse(
        UUID id,
        UUID conversationId,
        UUID messageId,
        UUID modelId,
        UUID aiRunStatusId,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatAiRunResponse from(ChatAiRun run) {
        return new ChatAiRunResponse(
                run.id().value(),
                run.conversationId().value(),
                run.messageId().value(),
                run.modelId().value(),
                run.aiRunStatusId().value(),
                run.createdAt(),
                run.updatedAt());
    }
}