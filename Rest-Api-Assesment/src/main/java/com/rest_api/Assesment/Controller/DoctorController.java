package com.rest_api.Assesment.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest_api.Assesment.Exception.InvalidIDException;
import com.rest_api.Assesment.Model.Doctor;
import com.rest_api.Assesment.Model.Patient;
import com.rest_api.Assesment.Service.DoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

	  @Autowired private DoctorService doctorService;

	 

	    @PostMapping("/add")
	    //for adding doctors
	    public Doctor addDoctor(@RequestBody Doctor doctor, Principal principal) {
	        String username= principal.getName();
	        return doctorService.addDoctor(doctor, username);
	          
	    }
	    @GetMapping("/patients/{doctorId}")
	    //getiing the list of patients by doctor id.
	    public ResponseEntity<List<Patient>> getAllPatients(@PathVariable int doctorId) throws InvalidIDException {
	        return ResponseEntity.ok(doctorService.getPatientsByDoctorId(doctorId));
	    }
}
