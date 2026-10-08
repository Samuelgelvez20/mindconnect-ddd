package com.mindconnect.application.ai.chatairunmetrics.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class ChatAiRunMetricsNotFoundApplicationException extends NotFoundApplicationException {

    public ChatAiRunMetricsNotFoundApplicationException(String id) {
        super("ChatAiRunMetrics not found with id: " + id);
    }
}