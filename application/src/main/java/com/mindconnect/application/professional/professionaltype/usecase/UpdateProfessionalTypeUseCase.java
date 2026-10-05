package com.mindconnect.application.professional.professionaltype.usecase;

import com.mindconnect.application.professional.professionaltype.command.UpdateProfessionalTypeCommand;
import com.mindconnect.application.professional.professionaltype.dto.ProfessionalTypeResponse;
import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeAlreadyExistsApplicationException;
import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;

public class UpdateProfessionalTypeUseCase {

    private final ProfessionalTypeRepository professionalTypeRepository;

    public UpdateProfessionalTypeUseCase(ProfessionalTypeRepository professionalTypeRepository) {
        this.professionalTypeRepository = professionalTypeRepository;
    }

    public ProfessionalTypeResponse execute(UpdateProfessionalTypeCommand command) {

        var professionalType = professionalTypeRepository.findById(command.id())
                .orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(command.id()));

        professionalType.update(command.name());

        if (professionalTypeRepository.existsByNameAndIdNot(professionalType.name(), professionalType.id())) {
            throw new ProfessionalTypeAlreadyExistsApplicationException(professionalType.name());
        }

        return ProfessionalTypeResponse.from(professionalTypeRepository.save(professionalType));
    }
}