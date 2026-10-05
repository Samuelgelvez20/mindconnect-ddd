package com.mindconnect.infrastructure.contact.emailcontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.contact.emailcontact.usecase.DeleteEmailContactUseCase;
import com.mindconnect.application.contact.emailcontact.usecase.GetEmailContactByIdUseCase;
import com.mindconnect.application.contact.emailcontact.usecase.ListEmailContactUseCase;
import com.mindconnect.application.contact.emailcontact.usecase.RegisterEmailContactUseCase;
import com.mindconnect.application.contact.emailcontact.usecase.UpdateEmailContactUseCase;
import com.mindconnect.domain.contact.emailcontact.port.repository.EmailContactRepository;
import com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.mappers.EmailContactPersistenceMapper;
import com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.repositories.EmailContactJpaRepository;
import com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.repositories.EmailContactRepositoryAdapter;

@Configuration
public class EmailContactBeansConfig {

    @Bean
    public EmailContactPersistenceMapper emailContactPersistenceMapper() {
        return new EmailContactPersistenceMapper();
    }

    @Bean
    public EmailContactRepository emailContactRepository(
            EmailContactJpaRepository jpaRepository,
            EmailContactPersistenceMapper mapper) {
        return new EmailContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEmailContactUseCase registerEmailContactUseCase(EmailContactRepository repository) {
        return new RegisterEmailContactUseCase(repository);
    }

    @Bean
    public GetEmailContactByIdUseCase getEmailContactByIdUseCase(EmailContactRepository repository) {
        return new GetEmailContactByIdUseCase(repository);
    }

    @Bean
    public ListEmailContactUseCase listEmailContactUseCase(EmailContactRepository repository) {
        return new ListEmailContactUseCase(repository);
    }

    @Bean
    public UpdateEmailContactUseCase updateEmailContactUseCase(EmailContactRepository repository) {
        return new UpdateEmailContactUseCase(repository);
    }

    @Bean
    public DeleteEmailContactUseCase deleteEmailContactUseCase(EmailContactRepository repository) {
        return new DeleteEmailContactUseCase(repository);
    }
}