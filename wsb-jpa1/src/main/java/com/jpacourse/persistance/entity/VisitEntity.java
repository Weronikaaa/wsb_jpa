package com.jpacourse.persistance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "doctor_id")
	private DoctorEntity doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "patient_id")
	private PatientEntity patient;

	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MedicalTreatmentEntity> medicalTreatments = new ArrayList<>();


//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "patint_id")
//	private PatientEntity patientEntity;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "doctor_id")
//	private DoctorEntity doctorEntity;
//
//	@Fetch(FetchMode.SELECT)
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "VISIT_ID")
//	private Collection<MedicalTreatmentEntity> medicalTreatmentEntityCollection;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
