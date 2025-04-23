package com.jpacourse.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;

@SpringBootTest
@Transactional
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private AddressDao addressDao;

    @Test
    @Rollback
    public void testShouldDeletePatientAndCascadeVisitsButNotDoctors() {
        // Dane z SQL
        Long patientIdToDelete = 22L; // Liam Davis
        Long doctorIdToCheck = 123L; // Alice Brown
        int initialVisitsCount = visitDao.findByPatientId(patientIdToDelete).size();

        System.out.println("=== PRZED USUNIĘCIEM ===");
        System.out.println("Pacjent istnieje: " + (patientDao.findOne(22L) != null));
        System.out.println("Liczba wizyt pacjenta: " + visitDao.findByPatientId(22L).size());

        // when
        patientService.delete(patientIdToDelete);

        // then
        assertThat(patientDao.findOne(patientIdToDelete)).isNull(); // 1- usuniety pacjent 
        assertThat(visitDao.findByPatientId(patientIdToDelete)).isEmpty(); //2- usuniete wizyty
        assertThat(doctorDao.findOne(doctorIdToCheck)).isNotNull(); // 3- nie usuniety lekarz
        assertThat(visitDao.findAll().size()).isEqualTo(3); // Powinno być 3 wizyty po usunięciu

        System.out.println("=== PO USUNIĘCIU ===");
        System.out.println("Pacjent istnieje: " + (patientDao.findOne(22L) != null));
        System.out.println("Liczba wizyt pacjenta: " + visitDao.findByPatientId(22L).size());
        System.out.println("Lekarz istnieje: " + (doctorDao.findOne(123L) != null));
    }

    @Test
    @Rollback
    public void testShouldFindPatientByIdWithCorrectTOStructure() {
        // given - pacjent z ID 23 (Emma Harris) 
        Long patientId = 23L;

        // when
        PatientTO patientTO = patientService.findById(patientId);

        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getId()).isEqualTo(patientId);
        assertThat(patientTO.getFirstName()).isEqualTo("Emma");
        assertThat(patientTO.getLastName()).isEqualTo("Harris");
        assertThat(patientTO.getPatientNumber()).isEqualTo("54321");
        assertThat(patientTO.getDateOfBirth()).isEqualTo("1992-12-25");
    }
}