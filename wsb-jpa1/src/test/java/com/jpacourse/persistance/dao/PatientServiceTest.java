package com.jpacourse.persistance.dao;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.PatientService;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
//@Transactional
@Sql(scripts = "/data.sql") // Jawnie ładujemy dane
class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private DoctorDao doctorDao;

    @Test
    void shouldFindVisitsByPatientId() {

        long patientId = 23;

        List<VisitTO> visits = patientService.getVisitsByPatientId(patientId);

        assertThat(visits).isNotEmpty();
        assertThat(visits).hasSize(2); // Emma ma 2 wizyty w danych testowych
        System.out.println("Znaleziono " + visits.size() + " wizyt dla pacjenta o ID " + patientId);
    }

    @Test
    @Sql(statements = {
            // Najpierw upewniamy się, że kolumny istnieją
            "ALTER TABLE patient DROP COLUMN IF EXISTS registration_date",
            "ALTER TABLE patient DROP COLUMN IF EXISTS version",
            "ALTER TABLE patient ADD COLUMN registration_date DATE",
            "ALTER TABLE patient ADD COLUMN version INTEGER DEFAULT 0",
            // Ustawiamy wartości domyślne
            "UPDATE patient SET registration_date = CURRENT_DATE, version = 0"
    })

    void shouldDeletePatientAndCascadeVisits() {

        // Given
        long patientId = 22;
        int doctorCount = doctorDao.findAll().size();

        List<Long> visitIds = visitDao.findByPatientId(patientId).stream()
                .map(VisitEntity::getId)
                .toList();

        // When
        patientService.deletePatient(patientId);

        // Then
        assertThat(patientService.findPatientById(patientId)).isNull();

        assertThat(visitIds).noneMatch(visitId -> visitDao.exists(visitId));

        assertThat(doctorDao.findAll()).hasSize(doctorCount);
    }

}