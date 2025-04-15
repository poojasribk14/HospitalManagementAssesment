package com.rest_api.Assesment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rest_api.Assesment.Exception.InvalidUserNameException;
import com.rest_api.Assesment.Model.*;
import com.rest_api.Assesment.Repository.*;
import com.rest_api.Assesment.Service.PatientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorPatientRepository doctorPatientRepository;

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private PatientService patientService;

    private User user;
    private Patient patient;
    private MedicalHistory medicalHistory;

    @BeforeEach
    public void setUp() {
        user = new User(1, "johnDoe", "password");

        medicalHistory = new MedicalHistory();
        medicalHistory.setIllness("Diabetes");
        medicalHistory.setNumOfYears(2);
        medicalHistory.setCurrentMedication("Insulin");

        patient = new Patient();
        patient.setId(1);
        patient.setName("John");
        patient.setAge(45);
        patient.setUser(user);
        patient.setMedicalHistory(medicalHistory);
    }

    @Test
    //test case for adding patients
    void addPatient() {
    	//case 1: geeting correct output.
        // Mocking to get the desired output.
        when(authRepository.findByUsername("johnDoe")).thenReturn(user);
        when(patientRepository.save(patient)).thenReturn(patient);
 
        try {
			assertEquals("John", patientService.addPatient(patient, "johnDoe").getName());
		} catch (InvalidUserNameException e) { 
		}
        
        
        //case 2: getting user not found exception.
        
        when(authRepository.findByUsername("pooja")).thenReturn(null);

        try {
			assertNotEquals("John", patientService.addPatient(patient, "pooja").getName());
		} catch (InvalidUserNameException e) { 
			assertEquals("User Name Already Exists...", e.getMessage());
		}
 
    }

     

    @Test
    void bookAppointment() {
    	//booking successfully!
        Doctor doctor = new Doctor(1, "Dr. Smith", Speciality.PHYSICIAN, null);
        DoctorPatient doctorPatient = new DoctorPatient(doctor, patient);

        assertEquals("Appointment Booked Successfully!", patientService.bookAppointment(doctorPatient));
        verify(doctorPatientRepository, times(1)).save(doctorPatient);
    }
}

