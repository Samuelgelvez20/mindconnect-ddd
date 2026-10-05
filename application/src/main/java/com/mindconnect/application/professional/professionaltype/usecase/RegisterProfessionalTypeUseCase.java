package com.mindconnect.application.professional.professionaltype.usecase;

import com.mindconnect.application.professional.professionaltype.command.RegisterProfessionalTypeCommand;
import com.mindconnect.application.professional.professionaltype.dto.ProfessionalTypeResponse;
import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;

public class RegisterProfessionalTypeUseCase {

    private final ProfessionalTypeRepository professionalTypeRepository;

    public RegisterProfessionalTypeUseCase(ProfessionalTypeRepository professionalTypeRepository) {
        this.professionalTypeRepository = professionalTypeRepository;
    }

    public ProfessionalTypeResponse execute(RegisterProfessionalTypeCommand command) {

        ProfessionalType professionalType = ProfessionalType.register(command.name());

        if (professionalTypeRepository.existsByName(professionalType.name())) {
            throw new ProfessionalTypeAlreadyExistsApplicationException(professionalType.name());
        }

        return ProfessionalTypeResponse.from(professionalTypeRepository.save(professionalType));
    }
}