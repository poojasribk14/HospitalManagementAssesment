package com.rest_api.Assesment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest_api.Assesment.Exception.InvalidUserNameException;
import com.rest_api.Assesment.Model.DoctorPatient;
import com.rest_api.Assesment.Model.MedicalHistory;
import com.rest_api.Assesment.Model.Patient;
import com.rest_api.Assesment.Model.User;
import com.rest_api.Assesment.Repository.AuthRepository;
import com.rest_api.Assesment.Repository.DoctorPatientRepository;
import com.rest_api.Assesment.Repository.MedicalHistoryRepository;
import com.rest_api.Assesment.Repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository; 
	@Autowired
	private DoctorPatientRepository doctorPatientRepository;
	
	@Autowired
	private MedicalHistoryRepository medicalHistoryRepository;
	@Autowired
	private AuthRepository authRepository;

	@Transactional
	public Patient addPatient(Patient patient, String username) throws InvalidUserNameException {
		
		User user = authRepository.findByUsername(username);
		if (user == null) {  
			throw new InvalidUserNameException("User Name Already Exists...");
	    }
 
		//getting the medical history from the passed patient object
		patient.setUser(user); 
		Patient saved = patientRepository.save(patient); 
		MedicalHistory medicalHistory = patient.getMedicalHistory();
	 
		if (medicalHistory != null) {
			//setting the passed patient in the medical history
		    medicalHistory.setPatient(patient); 
		    medicalHistoryRepository.save(medicalHistory); 
		}
		
		return saved;
	}

	public String bookAppointment(DoctorPatient doctorPatient) {
		doctorPatientRepository.save(doctorPatient);
		return "Appointment Booked Successfully!";
	}
}
