package com.jpacourse.persistance.dao;

import java.util.List;

import com.jpacourse.persistance.entity.VisitEntity;

public interface VisitDao extends Dao<VisitEntity, Long> {
    List<VisitEntity> findByPatientId(Long patientId); 
}