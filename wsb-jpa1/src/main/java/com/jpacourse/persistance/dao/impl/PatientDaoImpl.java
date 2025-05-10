package com.jpacourse.persistance.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.entity.AddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {


    @Autowired
    private DoctorDao doctorDao;

    @Override
    public PatientEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription){
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
        return patient;
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName",
                        PatientEntity.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findWithMoreThanXVisits(int minVisits) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p " +
                                "WHERE (SELECT COUNT(v) FROM VisitEntity v WHERE v.patient = p) > :minVisits",
                        PatientEntity.class)
                .setParameter("minVisits", (long) minVisits)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findByDateOfBirthBefore(LocalDate date) {
        return List.of();
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


