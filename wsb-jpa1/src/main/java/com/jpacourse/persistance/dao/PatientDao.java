package com.jpacourse.persistance.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.jpacourse.persistance.entity.PatientEntity;

public interface PatientDao extends Dao<PatientEntity, Long> {
    //dodac metode ktora na podstawie parametrow wejsciowych:
    //   ID pacjenta, ID doktora, data wizyty, opis wizyty
    //   utworzy nowa encje wizyty i doda ja do pacjenta w jednym wywolaniu - kaskadowy update pacjenta (merge).
    //   Npisz test do tej metody (Dao)

    PatientEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);

    List<PatientEntity> findByLastName(String lastName);

    List<PatientEntity> findWithMoreThanXVisits(int numOfVisits);

    List<PatientEntity> findByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);
}