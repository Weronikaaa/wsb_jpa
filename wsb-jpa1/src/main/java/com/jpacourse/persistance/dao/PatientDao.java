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

    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);
    List<PatientEntity> findByLastName(String lastName);
    List<PatientEntity> findWithMoreThanXVisits(int x);
    List<PatientEntity> findByDateOfBirthBefore(LocalDate date);
}

