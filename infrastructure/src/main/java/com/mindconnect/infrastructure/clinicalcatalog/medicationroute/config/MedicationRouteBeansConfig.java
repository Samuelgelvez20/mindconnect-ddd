package com.mindconnect.infrastructure.clinicalcatalog.medicationroute.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.DeleteMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.GetMedicationRouteByIdUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.ListMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.RegisterMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.UpdateMedicationRouteUseCase;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;
import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.mappers.MedicationRoutePersistenceMapper;
import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.repositories.MedicationRouteJpaRepository;
import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.repositories.MedicationRouteRepositoryAdapter;

@Configuration
public class MedicationRouteBeansConfig {

    @Bean
    public MedicationRoutePersistenceMapper medicationRoutePersistenceMapper() {
        return new MedicationRoutePersistenceMapper();
    }

    @Bean
    public MedicationRouteRepository medicationRouteRepository(
            MedicationRouteJpaRepository jpaRepository,
            MedicationRoutePersistenceMapper mapper) {
        return new MedicationRouteRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.medicationroute.usecase.RegisterMedicationRouteUseCase registerMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.medicationroute.usecase.RegisterMedicationRouteUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.medicationroute.usecase.GetMedicationRouteByIdUseCase getMedicationRouteByIdUseCase(MedicationRouteRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.medicationroute.usecase.GetMedicationRouteByIdUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.medicationroute.usecase.ListMedicationRouteUseCase listMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.medicationroute.usecase.ListMedicationRouteUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.medicationroute.usecase.UpdateMedicationRouteUseCase updateMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.medicationroute.usecase.UpdateMedicationRouteUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.medicationroute.usecase.DeleteMedicationRouteUseCase deleteMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.medicationroute.usecase.DeleteMedicationRouteUseCase(repository);
    }
}