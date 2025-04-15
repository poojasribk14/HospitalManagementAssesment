package com.rest_api.Assesment.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class DoctorPatient {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate appointmentDate;
    @ManyToOne 
    private Doctor doctor;

    @ManyToOne 
    private Patient patient;

	public DoctorPatient(Doctor doctor, Patient patient) {
		this.doctor=doctor;
		this.patient=patient;
		appointmentDate=LocalDate.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
