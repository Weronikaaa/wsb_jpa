package com.jpacourse.persistance.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;

import jakarta.persistence.TypedQuery;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {


    @Autowired
    private DoctorDao doctorDao;

    @Override
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription){
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = doctorDao.findOne(doctorId);

        if(patient != null && doctor != null){
            VisitEntity visit = new VisitEntity();
            visit.setDoctor(doctor);
            visit.setPatient(patient);
            visit.setTime(visitDate);
            visit.setDescription(visitDescription);

            patient.getVisits().add(visit);

            update(patient); // metoda dao, merge() wykonywany w srodku (abstrakcja nad merge())
        } else {
            throw new EntityNotFoundException(patientId);
        }
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
                "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName", 
                PatientEntity.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findWithMoreThanXVisits(int x) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
                "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :x", 
                PatientEntity.class);
        query.setParameter("x", x);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findByDateOfBirthBefore(LocalDate date) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
                "SELECT p FROM PatientEntity p WHERE p.dateOfBirth < :date", PatientEntity.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    // //Wersja z enity manager a nie Dao zeby uzyc merge()
    // @PersistenceContext
    // private EntityManager entityManager;

    // @Override
    // public void addVisitToPatient(Long patientId, Long doctorId, 
    //                             LocalDateTime visitDate, String visitDescription) {
    //     PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
    //     DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

    //     if (patient == null || doctor == null) {
    //         throw new IllegalArgumentException("Patient or Doctor not found");
    //     }

    //     VisitEntity newVisit = new VisitEntity();
    //     newVisit.setTime(visitDate);
    //     newVisit.setDescription(visitDescription);
    //     newVisit.setPatient(patient);
    //     newVisit.setDoctor(doctor);

    //     patient.getVisits().add(newVisit);
    //     entityManager.merge(patient); //kaskada
    //     // merge() synchronizuje stan encji z bazą danych
    // }

} 


