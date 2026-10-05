package com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;

public interface MentalStatusExamRepository {

    MentalStatusExam save(MentalStatusExam mentalStatusExam);

    Optional<MentalStatusExam> findById(MentalStatusExamId id);

    List<MentalStatusExam> findAll();

    List<MentalStatusExam> findByEncounterId(EncounterId encounterId);

    void delete(MentalStatusExam mentalStatusExam);
}