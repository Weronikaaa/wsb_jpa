package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {

    //Znajdz pacjentow po nazwisku

    public List<DoctorEntity> findByDocName(String pName){
        return entityManager.createQuery(" select doc from DoctorEntity doc " + "where doc.lastName =:param1 ", DoctorEntity.class)
                .setParameter("param1", pName).getResultList();
    }


    public List<DoctorEntity> findByVisitDescription(String pDesc){
        return entityManager.createQuery(" select doc from DoctorEntity doc " + " join doc.visits visit " + " where visit.description like :param1", DoctorEntity.class)
                .setParameter("param1", "%"+pDesc+"%").getResultList();
    }


    public List<DoctorEntity> findByMinNrOfVisits(long pNumOfVisit){
        return entityManager.createQuery(" select doc from DoctorEntity doc " + " where size(doc.visits) > :param1 ", DoctorEntity.class)
                .setParameter("param1", pNumOfVisit).getResultList();
    }


    public List<DoctorEntity> findByVisitDateRange(LocalDateTime pDateFrom, LocalDateTime pDateTo){
        return entityManager.createQuery(" select doc from DoctorEntity doc " + " join doc.visits visit " + " where visit.time between :param1 and :param2 ", DoctorEntity.class)
                .setParameter("param1", pDateFrom)
                .setParameter("param2", pDateTo)
                .getResultList();
    }
}