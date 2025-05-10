package com.jpacourse.persistance.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private VisitDao visitDao;

    @Transactional
    @Rollback
    @Test
    public void testShouldFindPatientById() {
        // given
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Liam");
        patient.setLastName("Davis");
        patient.setTelephoneNumber("555123456");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        PatientEntity savedPatient = patientDao.save(patient);

        // when
        PatientEntity patientEntity = patientDao.findOne(savedPatient.getId());

        // then
        assertThat(patientEntity).isNotNull();
        assertThat(patientEntity.getFirstName()).isEqualTo("Liam");
        assertThat(patientEntity.getLastName()).isEqualTo("Davis");
        assertThat(patientEntity.getTelephoneNumber()).isEqualTo("555123456");
        assertThat(patientEntity.getDateOfBirth()).isEqualTo((LocalDate.of(1980, 1, 1)));

    }

    @Transactional
    @Rollback
    @Test
    public void testShouldFindPatientByLastName() {
        // given
        String commonLastName = "Smith";

        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("John");
        patient1.setLastName(commonLastName);
        patient1.setTelephoneNumber("555111222");
        patient1.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patientDao.save(patient1);

        // when
        List<PatientEntity> foundPatients = patientDao.findByLastName(commonLastName);
        // then
        assertThat(foundPatients)
                .hasSize(1)
                .extracting(PatientEntity::getLastName)
                .containsOnly(commonLastName);

        assertThat(foundPatients)
                .extracting(PatientEntity::getFirstName)
                .containsExactlyInAnyOrder("John");

    }
    @Transactional
    @Rollback
    @Test
    public void testShouldFindPatientsWithMoreThanXVisits() {
        // given
        int minVisits = 2;

        // Pacjent z 1 wizytą (NIE powinien się znaleźć w wynikach)
        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("John");
        patient1.setLastName("Doe");
        patient1.setTelephoneNumber("555111111");
        patient1.setDateOfBirth(LocalDate.of(1985, 5, 15));
        PatientEntity savedPatient1 = patientDao.save(patient1);

        // Dodanie 1 wizyty
        VisitEntity visit1 = new VisitEntity();
        visit1.setDescription("Single visit");
        visit1.setPatient(savedPatient1);
        visit1.setTime(LocalDateTime.now());
        visitDao.save(visit1);

        // Pacjent z 3 wizytami (POWINIEN się znaleźć)
        PatientEntity patient2 = new PatientEntity();
        patient2.setFirstName("Emma");
        patient2.setLastName("Harris");
        patient2.setTelephoneNumber("555222222");
        patient2.setDateOfBirth(LocalDate.of(1990, 8, 20));
        PatientEntity savedPatient2 = patientDao.save(patient2);

        // Dodanie 3 wizyt
        VisitEntity visit2 = new VisitEntity();
        visit2.setDescription("First visit");
        visit2.setPatient(savedPatient2);
        visit2.setTime(LocalDateTime.now());
        visitDao.save(visit2);

        VisitEntity visit3 = new VisitEntity();
        visit3.setDescription("Second visit");
        visit3.setPatient(savedPatient2);
        visit3.setTime(LocalDateTime.now());
        visitDao.save(visit3);

        VisitEntity visit4 = new VisitEntity();
        visit4.setDescription("Third visit");
        visit4.setPatient(savedPatient2);
        visit4.setTime(LocalDateTime.now());
        visitDao.save(visit4);

        // when
        List<PatientEntity> result = patientDao.findWithMoreThanXVisits(minVisits);

        // then
        assertThat(result)
                .hasSize(1)
                .extracting(PatientEntity::getLastName)
                .containsExactly("Harris");
    }

}