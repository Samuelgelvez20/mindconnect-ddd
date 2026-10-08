package com.mindconnect.infrastructure.contact.relationshiptype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.contact.relationshiptype.usecase.DeleteRelationshipTypeUseCase;
import com.mindconnect.application.contact.relationshiptype.usecase.GetRelationshipTypeByIdUseCase;
import com.mindconnect.application.contact.relationshiptype.usecase.ListRelationshipTypeUseCase;
import com.mindconnect.application.contact.relationshiptype.usecase.RegisterRelationshipTypeUseCase;
import com.mindconnect.application.contact.relationshiptype.usecase.UpdateRelationshipTypeUseCase;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;
import com.mindconnect.infrastructure.contact.relationshiptype.adapters.out.persistence.mappers.RelationshipTypePersistenceMapper;
import com.mindconnect.infrastructure.contact.relationshiptype.adapters.out.persistence.repositories.RelationshipTypeJpaRepository;
import com.mindconnect.infrastructure.contact.relationshiptype.adapters.out.persistence.repositories.RelationshipTypeRepositoryAdapter;

@Configuration
public class RelationshipTypeBeansConfig {

    @Bean
    public RelationshipTypePersistenceMapper relationshipTypePersistenceMapper() {
        return new RelationshipTypePersistenceMapper();
    }

    @Bean
    public RelationshipTypeRepository relationshipTypeRepository(
            RelationshipTypeJpaRepository jpaRepository,
            RelationshipTypePersistenceMapper mapper) {
        return new RelationshipTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterRelationshipTypeUseCase registerRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new RegisterRelationshipTypeUseCase(repository);
    }

    @Bean
    public GetRelationshipTypeByIdUseCase getRelationshipTypeByIdUseCase(RelationshipTypeRepository repository) {
        return new GetRelationshipTypeByIdUseCase(repository);
    }

    @Bean
    public ListRelationshipTypeUseCase listRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new ListRelationshipTypeUseCase(repository);
    }

    @Bean
    public UpdateRelationshipTypeUseCase updateRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new UpdateRelationshipTypeUseCase(repository);
    }

    @Bean
    public DeleteRelationshipTypeUseCase deleteRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new DeleteRelationshipTypeUseCase(repository);
    }
}