package com.mindconnect.infrastructure.contact.phonecontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.contact.phonecontact.usecase.DeletePhoneContactUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.GetPhoneContactByIdUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.ListPhoneContactUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.RegisterPhoneContactUseCase;
import com.mindconnect.application.contact.phonecontact.usecase.UpdatePhoneContactUseCase;
import com.mindconnect.domain.contact.phonecontact.port.repository.PhoneContactRepository;
import com.mindconnect.infrastructure.contact.phonecontact.adapters.out.persistence.mappers.PhoneContactPersistenceMapper;
import com.mindconnect.infrastructure.contact.phonecontact.adapters.out.persistence.repositories.PhoneContactJpaRepository;
import com.mindconnect.infrastructure.contact.phonecontact.adapters.out.persistence.repositories.PhoneContactRepositoryAdapter;

@Configuration
public class PhoneContactBeansConfig {

    @Bean
    public PhoneContactPersistenceMapper phoneContactPersistenceMapper() {
        return new PhoneContactPersistenceMapper();
    }

    @Bean
    public PhoneContactRepository phoneContactRepository(
            PhoneContactJpaRepository jpaRepository,
            PhoneContactPersistenceMapper mapper) {
        return new PhoneContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPhoneContactUseCase registerPhoneContactUseCase(PhoneContactRepository repository) {
        return new RegisterPhoneContactUseCase(repository);
    }

    @Bean
    public GetPhoneContactByIdUseCase getPhoneContactByIdUseCase(PhoneContactRepository repository) {
        return new GetPhoneContactByIdUseCase(repository);
    }

    @Bean
    public ListPhoneContactUseCase listPhoneContactUseCase(PhoneContactRepository repository) {
        return new ListPhoneContactUseCase(repository);
    }

    @Bean
    public UpdatePhoneContactUseCase updatePhoneContactUseCase(PhoneContactRepository repository) {
        return new UpdatePhoneContactUseCase(repository);
    }

    @Bean
    public DeletePhoneContactUseCase deletePhoneContactUseCase(PhoneContactRepository repository) {
        return new DeletePhoneContactUseCase(repository);
    }
}