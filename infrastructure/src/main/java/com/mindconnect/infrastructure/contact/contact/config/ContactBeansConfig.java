package com.mindconnect.infrastructure.contact.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.contact.contact.usecase.DeleteContactUseCase;
import com.mindconnect.application.contact.contact.usecase.GetContactByIdUseCase;
import com.mindconnect.application.contact.contact.usecase.ListContactUseCase;
import com.mindconnect.application.contact.contact.usecase.RegisterContactUseCase;
import com.mindconnect.application.contact.contact.usecase.UpdateContactUseCase;
import com.mindconnect.domain.contact.contact.port.repository.ContactRepository;
import com.mindconnect.infrastructure.contact.contact.adapters.out.persistence.mappers.ContactPersistenceMapper;
import com.mindconnect.infrastructure.contact.contact.adapters.out.persistence.repositories.ContactJpaRepository;
import com.mindconnect.infrastructure.contact.contact.adapters.out.persistence.repositories.ContactRepositoryAdapter;

@Configuration
public class ContactBeansConfig {

    @Bean
    public ContactPersistenceMapper contactPersistenceMapper() {
        return new ContactPersistenceMapper();
    }

    @Bean
    public ContactRepository contactRepository(
            ContactJpaRepository jpaRepository,
            ContactPersistenceMapper mapper) {
        return new ContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterContactUseCase registerContactUseCase(ContactRepository repository) {
        return new RegisterContactUseCase(repository);
    }

    @Bean
    public GetContactByIdUseCase getContactByIdUseCase(ContactRepository repository) {
        return new GetContactByIdUseCase(repository);
    }

    @Bean
    public ListContactUseCase listContactUseCase(ContactRepository repository) {
        return new ListContactUseCase(repository);
    }

    @Bean
    public UpdateContactUseCase updateContactUseCase(ContactRepository repository) {
        return new UpdateContactUseCase(repository);
    }

    @Bean
    public DeleteContactUseCase deleteContactUseCase(ContactRepository repository) {
        return new DeleteContactUseCase(repository);
    }
}