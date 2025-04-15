package com.rest_api.Assesment.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest_api.Assesment.Exception.InvalidIDException;
import com.rest_api.Assesment.Model.Doctor;
import com.rest_api.Assesment.Model.DoctorPatient;
import com.rest_api.Assesment.Model.Patient;
import com.rest_api.Assesment.Model.User;
import com.rest_api.Assesment.Repository.AuthRepository;
import com.rest_api.Assesment.Repository.DoctorPatientRepository;
import com.rest_api.Assesment.Repository.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorPatientRepository doctorPatientRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private AuthRepository authRepository;
	
	Logger logger = LoggerFactory.getLogger(DoctorService.class);

	// adding a doctor
	public Doctor addDoctor(Doctor doctor, String username) {

		User user = authRepository.findByUsername(username);
		if (user != null)
			doctor.setUser(user);
		// if doctor is not null then add.
		if (doctor.getUser() != null && doctor.getName() != null)
			logger.info("Doctor {} added successfully", doctor.getName());
			return doctorRepository.save(doctor);
		
	}

	public List<Patient> getPatientsByDoctorId(int doctorId) throws InvalidIDException {

		//checking if my doctor id is there or not
		Optional<Doctor> optional = doctorRepository.findById(doctorId);
		if (optional.isEmpty()) {
			logger.warn("Invalid doctor ID: {}. No doctor found.", doctorId);
			throw new InvalidIDException("Doctor Id is inavlid!!!");
		}
		//storing the doctor details in the doctor object.
		Doctor doctor = optional.get();
		logger.info("Doctor found: {}", doctor);

		//storing the list of doctor patients details by the doctor object.
		List<DoctorPatient> doctorPatients = doctorPatientRepository.findByDoctor(doctor);

		//generating a list of patients for the doctor id given,
		//by fetching from doctorPatients repository.
		List<Patient> patients = new ArrayList<>();
		doctorPatients.forEach(dp -> patients.add(dp.getPatient()));

		return patients;
	}
}
