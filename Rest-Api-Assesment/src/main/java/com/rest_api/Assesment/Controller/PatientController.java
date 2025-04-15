package com.rest_api.Assesment.Controller;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest_api.Assesment.Exception.InvalidIDException;
import com.rest_api.Assesment.Exception.InvalidUserNameException;
import com.rest_api.Assesment.Model.DoctorPatient;
import com.rest_api.Assesment.Model.Patient;
import com.rest_api.Assesment.Service.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping("/add")
	public ResponseEntity<String> addPatient(@RequestBody Patient patient) throws InvalidUserNameException, InvalidIDException {
		String savedPatient = patientService.addPatient(patient);
        return ResponseEntity.ok(savedPatient); 
	}

	@PostMapping("/appointment")
	public ResponseEntity<String> makeAppointment(@RequestBody DoctorPatient appointment) {
		return ResponseEntity.ok(patientService.bookAppointment(appointment));
	}
}
