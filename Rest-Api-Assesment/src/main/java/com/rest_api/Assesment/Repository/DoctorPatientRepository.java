package com.rest_api.Assesment.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest_api.Assesment.Model.Doctor;
import com.rest_api.Assesment.Model.DoctorPatient;

public interface DoctorPatientRepository extends JpaRepository<DoctorPatient, Integer> {

	List<DoctorPatient> findByDoctor(Doctor doctor);
	
}