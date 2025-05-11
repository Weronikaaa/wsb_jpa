package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.Specialization;
import com.jpacourse.service.DoctorService;
import com.jpacourse.service.PatientService;
import com.jpacourse.service.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VisitServiceTest {

   @Autowired
   private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

   @Autowired
   private DoctorService doctorService;

    @Autowired
    private DoctorDao doctorDao;

   @Autowired
   private VisitService visitService;

    @Autowired
    private VisitDao visitDao;

   @Test
   void shouldSaveVisitForPatientAndDoctor() {
       // Given
       AddressEntity address = new AddressEntity();
       address.setAddressLine1("ul. Testowa 123");
       address.setCity("Warszawa");
       address.setPostalCode("00-001");

       PatientEntity patient = new PatientEntity();
       patient.setFirstName("Anna");
       patient.setLastName("Kowalska");
       patient.setDateOfBirth(LocalDate.of(1990, 5, 15));
       patient.setTelephoneNumber("123456789");
       patient.setPatientNumber("PAT-" + System.currentTimeMillis());
       patient.setAddress(address);
       PatientEntity savedPatient = patientDao.save(patient);

       DoctorEntity doctor = new DoctorEntity();
       doctor.setFirstName("Jan");
       doctor.setLastName("Lekarz");
       doctor.setSpecialization(Specialization.CARDIOLOGIST);
       doctor.setDoctorNumber("1");
       doctor.setTelephoneNumber("123456789");
       doctor.setAddress(address);
       DoctorEntity savedDoctor = doctorDao.save(doctor);

       VisitEntity visit = new VisitEntity();
       visit.setPatient(savedPatient);
       visit.setDoctor(savedDoctor);
       visit.setTime(LocalDateTime.now());
       visit.setDescription("Konsultacja kardiologiczna");

       // When
       VisitEntity savedVisit = visitDao.save(visit);

       // Then
       assertNotNull(savedVisit.getId(), "Visit should have an ID");
       assertEquals(savedPatient.getId(), savedVisit.getPatient().getId(), "Visit should be linked to the patient");
       assertEquals(savedDoctor.getId(), savedVisit.getDoctor().getId(), "Visit should be linked to the doctor");
       assertEquals("Konsultacja kardiologiczna", savedVisit.getDescription(), "Visit description should match");
       assertNotNull(visitService.findById(savedVisit.getId()), "Visit should be found in the database");
   }
}