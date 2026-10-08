package com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;

public interface ClinicalRecordStatusRepository {

    ClinicalRecordStatus save(ClinicalRecordStatus clinicalRecordStatus);

    Optional<ClinicalRecordStatus> findById(ClinicalRecordStatusId id);

    List<ClinicalRecordStatus> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, ClinicalRecordStatusId id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, ClinicalRecordStatusId id);

    void delete(ClinicalRecordStatus clinicalRecordStatus);
}