package com.mindconnect.infrastructure.ai.chatairunmetrics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.ai.chatairunmetrics.usecase.DeleteChatAiRunMetricsUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.GetChatAiRunMetricsByIdUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.ListChatAiRunMetricsUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.RegisterChatAiRunMetricsUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.UpdateChatAiRunMetricsUseCase;
import com.mindconnect.domain.ai.chatairunmetrics.port.repository.ChatAiRunMetricsRepository;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.mappers.ChatAiRunMetricsPersistenceMapper;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.repositories.ChatAiRunMetricsJpaRepository;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.repositories.ChatAiRunMetricsRepositoryAdapter;

@Configuration
class ChatAiRunMetricsBeansConfig {

    @Bean
    ChatAiRunMetricsPersistenceMapper chatAiRunMetricsPersistenceMapper() {
        return new ChatAiRunMetricsPersistenceMapper();
    }

    @Bean
    ChatAiRunMetricsRepository chatAiRunMetricsRepository(
            ChatAiRunMetricsJpaRepository jpaRepository,
            ChatAiRunMetricsPersistenceMapper mapper) {
        return new ChatAiRunMetricsRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    RegisterChatAiRunMetricsUseCase registerChatAiRunMetricsUseCase(ChatAiRunMetricsRepository repository) {
        return new RegisterChatAiRunMetricsUseCase(repository);
    }

    @Bean
    GetChatAiRunMetricsByIdUseCase getChatAiRunMetricsByIdUseCase(ChatAiRunMetricsRepository repository) {
        return new GetChatAiRunMetricsByIdUseCase(repository);
    }

    @Bean
    ListChatAiRunMetricsUseCase listChatAiRunMetricsUseCase(ChatAiRunMetricsRepository repository) {
        return new ListChatAiRunMetricsUseCase(repository);
    }

    @Bean
    UpdateChatAiRunMetricsUseCase updateChatAiRunMetricsUseCase(ChatAiRunMetricsRepository repository) {
        return new UpdateChatAiRunMetricsUseCase(repository);
    }

    @Bean
    DeleteChatAiRunMetricsUseCase deleteChatAiRunMetricsUseCase(ChatAiRunMetricsRepository repository) {
        return new DeleteChatAiRunMetricsUseCase(repository);
    }
}