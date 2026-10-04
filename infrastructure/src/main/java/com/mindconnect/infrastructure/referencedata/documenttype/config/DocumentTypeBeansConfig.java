package com.mindconnect.infrastructure.referencedata.documenttype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.referencedata.documenttype.usecase.DeleteDocumentTypeUseCase;
import com.mindconnect.application.referencedata.documenttype.usecase.GetDocumentTypeByIdUseCase;
import com.mindconnect.application.referencedata.documenttype.usecase.ListDocumentTypeUseCase;
import com.mindconnect.application.referencedata.documenttype.usecase.RegisterDocumentTypeUseCase;
import com.mindconnect.application.referencedata.documenttype.usecase.UpdateDocumentTypeUseCase;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;
import com.mindconnect.infrastructure.referencedata.documenttype.adapters.out.persistence.mappers.DocumentTypePersistenceMapper;
import com.mindconnect.infrastructure.referencedata.documenttype.adapters.out.persistence.repositories.DocumentTypeJpaRepository;
import com.mindconnect.infrastructure.referencedata.documenttype.adapters.out.persistence.repositories.DocumentTypeRepositoryAdapter;

@Configuration
public class DocumentTypeBeansConfig {

    @Bean
    public DocumentTypePersistenceMapper documentTypePersistenceMapper() {
        return new DocumentTypePersistenceMapper();
    }

    @Bean
    public DocumentTypeRepository documentTypeRepository(
            DocumentTypeJpaRepository jpaRepository,
            DocumentTypePersistenceMapper mapper) {
        return new DocumentTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterDocumentTypeUseCase registerDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new RegisterDocumentTypeUseCase(repository);
    }

    @Bean
    public GetDocumentTypeByIdUseCase getDocumentTypeByIdUseCase(DocumentTypeRepository repository) {
        return new GetDocumentTypeByIdUseCase(repository);
    }

    @Bean
    public ListDocumentTypeUseCase listDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new ListDocumentTypeUseCase(repository);
    }

    @Bean
    public UpdateDocumentTypeUseCase updateDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new UpdateDocumentTypeUseCase(repository);
    }

    @Bean
    public DeleteDocumentTypeUseCase deleteDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new DeleteDocumentTypeUseCase(repository);
    }
}