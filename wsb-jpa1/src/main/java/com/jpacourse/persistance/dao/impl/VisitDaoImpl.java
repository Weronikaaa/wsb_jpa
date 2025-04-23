package com.jpacourse.persistance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.VisitEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class VisitDaoImpl extends AbstractDao<VisitEntity, Long> implements VisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VisitEntity> findByPatientId(Long patientId) {
        return entityManager.createQuery(
            "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId", 
            VisitEntity.class)
            .setParameter("patientId", patientId)
            .getResultList();
    }
}