package com.rest_api.Assesment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rest_api.Assesment.Exception.InvalidIDException;
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
    void addPatientTest() {
        // Case 1: Patient added successfully (no existing user)

        // Setup: Assume getUser() returns a mock User object
        when(patient.getUser()).thenReturn(user);
        when(patientRepository.findByUser(user)).thenReturn(null); // user does not exist
        when(medicalHistoryRepository.save(patient.getMedicalHistory())).thenReturn(patient.getMedicalHistory());
        when(patientRepository.save(patient)).thenReturn(patient);

        try {
            String result = patientService.addPatient(patient);
            assertEquals("Patient added successfully", result);
        } catch (InvalidIDException e) {
            fail("Exception should not have been thrown");
        }

        // Case 2: Patient with same user already exists
        when(patientRepository.findByUser(user)).thenReturn(patient); // user already exists

        try {
            patientService.addPatient(patient);
            fail("Expected InvalidIDException to be thrown");
        } catch (InvalidIDException e) {
            assertEquals("userId already exist", e.getMessage());
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

