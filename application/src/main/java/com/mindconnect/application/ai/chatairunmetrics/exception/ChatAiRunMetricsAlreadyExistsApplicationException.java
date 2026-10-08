package com.mindconnect.application.ai.chatairunmetrics.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ChatAiRunMetricsAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ChatAiRunMetricsAlreadyExistsApplicationException(String aiRunId) {
        super("ChatAiRunMetrics already exists for aiRun: " + aiRunId);
    }
}