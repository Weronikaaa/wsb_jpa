package com.jpacourse.persistance.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.persistance.entity.PatientEntity;
import org.springframework.transaction.support.TransactionTemplate;

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
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("123 Main St");
        address.setCity("New York");
        address.setPostalCode("10001");

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Liam");
        patient.setLastName("Davis");
        patient.setTelephoneNumber("555123456");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient.setPatientNumber("PAT12345");
        patient.setAddress(address);
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

        AddressEntity address1 = new AddressEntity();
        address1.setAddressLine1("123 Main St");
        address1.setCity("New York");
        address1.setPostalCode("10001");

        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("John");
        patient1.setLastName(commonLastName);
        patient1.setTelephoneNumber("555111222");
        patient1.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patient1.setPatientNumber("PAT12345");
        patient1.setAddress(address1);
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

        AddressEntity address1 = new AddressEntity();
        address1.setAddressLine1("123 Main St");
        address1.setCity("New York");
        address1.setPostalCode("10001");

        AddressEntity address2 = new AddressEntity();
        address2.setAddressLine1("456 Main St");
        address2.setCity("New Jersey");
        address2.setPostalCode("14501");


        // Pacjent z 1 wizytą (NIE powinien się znaleźć w wynikach)
        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("John");
        patient1.setLastName("Doe");
        patient1.setTelephoneNumber("555111111");
        patient1.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patient1.setPatientNumber("PAT12345");
        patient1.setAddress(address1);
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
        patient2.setPatientNumber("PAT12995");
        patient2.setAddress(address2);
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

    @Transactional
    @Rollback
    @Test
    public void testShouldFindPatientsByRegistrationDateRange() {
        // given
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(7); // Tydzień temu
        LocalDate endDate = today.plusDays(1);    // Jutro

        // Create a shared address for test patients
        AddressEntity testAddress = new AddressEntity();
        testAddress.setAddressLine1("Test Street");
        testAddress.setCity("Test City");
        testAddress.setPostalCode("00-000");

        AddressEntity testAddress1 = new AddressEntity();
        testAddress1.setAddressLine1("Test Street");
        testAddress1.setCity("Test City");
        testAddress1.setPostalCode("00-000");

        AddressEntity testAddress2 = new AddressEntity();
        testAddress2.setAddressLine1("Test Street");
        testAddress2.setCity("Test City");
        testAddress2.setPostalCode("00-000");

        // Pacjent zarejestrowany 2 tygodnie temu (NIE powinien się znaleźć)
        PatientEntity oldPatient = new PatientEntity();
        oldPatient.setFirstName("Old");
        oldPatient.setLastName("Patient");
        oldPatient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        oldPatient.setTelephoneNumber("123456789");
        oldPatient.setPatientNumber("PAT00001");
        oldPatient.setRegistrationDate(today.minusWeeks(2));
        oldPatient.setAddress(testAddress);
        patientDao.save(oldPatient);

        // Pacjent zarejestrowany 3 dni temu (POWINIEN się znaleźć)
        PatientEntity recentPatient1 = new PatientEntity();
        recentPatient1.setFirstName("Recent");
        recentPatient1.setLastName("Patient1");
        recentPatient1.setDateOfBirth(LocalDate.of(1990, 1, 1));
        recentPatient1.setTelephoneNumber("123456789");
        recentPatient1.setPatientNumber("PAT00002");
        recentPatient1.setAddress(testAddress1);
        recentPatient1.setRegistrationDate(today.minusDays(3));
        patientDao.save(recentPatient1);

        // Pacjent zarejestrowany dzisiaj (POWINIEN się znaleźć)
        PatientEntity recentPatient2 = new PatientEntity();
        recentPatient2.setFirstName("Today");
        recentPatient2.setLastName("Patient2");
        recentPatient2.setDateOfBirth(LocalDate.of(1995, 1, 1));
        recentPatient2.setTelephoneNumber("123456789");
        recentPatient2.setPatientNumber("PAT00003");
        recentPatient2.setRegistrationDate(today);
        recentPatient2.setAddress(testAddress2);
        patientDao.save(recentPatient2);

        // when
        List<PatientEntity> foundPatients = patientDao.findByRegistrationDateBetween(startDate, endDate);

        // then
        assertThat(foundPatients)
                .hasSize(2)
                .extracting(PatientEntity::getLastName)
                .containsExactlyInAnyOrder("Patient1", "Patient2");
    }


}