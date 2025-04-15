package com.rest_api.Assesment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest_api.Assesment.Exception.InvalidIDException;
import com.rest_api.Assesment.Model.DoctorPatient;
import com.rest_api.Assesment.Model.MedicalHistory;
import com.rest_api.Assesment.Model.Patient; 
import com.rest_api.Assesment.Repository.DoctorPatientRepository;
import com.rest_api.Assesment.Repository.MedicalHistoryRepository;
import com.rest_api.Assesment.Repository.PatientRepository;
 

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository; 
	@Autowired
	private DoctorPatientRepository doctorPatientRepository;
	
	@Autowired
	private MedicalHistoryRepository medicalHistoryRepository;
	 

	public String addPatient(Patient patient) throws InvalidIDException {
		//saving medical history fetched from patient.
		MedicalHistory savedHistory = medicalHistoryRepository.save(patient.getMedicalHistory());
		patient.setMedicalHistory(savedHistory);
		Patient user = patientRepository.findByUser(patient.getUser());
		if(user != null)
			throw new InvalidIDException("userId already exist");
		patientRepository.save(patient);
		return "Patient added successfully";
	}

	public String bookAppointment(DoctorPatient doctorPatient) {
		doctorPatientRepository.save(doctorPatient);
		return "Appointment Booked Successfully!";
	}
}
