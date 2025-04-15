package com.rest_api.Assesment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.rest_api.Assesment.Exception.InvalidIDException;
import com.rest_api.Assesment.Model.Doctor;
import com.rest_api.Assesment.Model.DoctorPatient;
import com.rest_api.Assesment.Model.MedicalHistory;
import com.rest_api.Assesment.Model.Patient;
import com.rest_api.Assesment.Model.Speciality;
import com.rest_api.Assesment.Model.User;
import com.rest_api.Assesment.Repository.DoctorPatientRepository;
import com.rest_api.Assesment.Repository.DoctorRepository;
import com.rest_api.Assesment.Service.DoctorService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorPatientRepository doctorPatientRepository;

    @InjectMocks
    private DoctorService doctorService;
    
    private Doctor doctor;
    private Patient patient1;
    private Patient patient2;
    private DoctorPatient doctorPatient1;
    private DoctorPatient doctorPatient2;
    private User user1;
    private User user2;
    private MedicalHistory medicalHistory1;
    private MedicalHistory medicalHistory2;

    @BeforeEach
    public void setUp() {
        // Create users for patients
        user1 = new User(1, "aliceUser", "password"); 
        user2 = new User(2, "bobUser", "password"); 
        
        // Create MedicalHistory objects for patients
        medicalHistory1 = new MedicalHistory();
        medicalHistory1.setIllness("Cardiac Issues");
        medicalHistory1.setNumOfYears(5);  
        medicalHistory1.setCurrentMedication("Beta Blockers, Aspirin");

        medicalHistory2 = new MedicalHistory();
        medicalHistory2.setIllness("Heart Disease");
        medicalHistory2.setNumOfYears(3); 
        medicalHistory2.setCurrentMedication("Statins, ACE Inhibitors");

        // Create patients with medical histories and users
        patient1 = new Patient();
        patient1.setId(1);
        patient1.setName("Alice");
        patient1.setAge(30);
        patient1.setUser(user1);
        patient1.setMedicalHistory(medicalHistory1);

        patient2 = new Patient();
        patient2.setId(2);
        patient2.setName("Bob");
        patient2.setAge(40);
        patient2.setUser(user2);
        patient2.setMedicalHistory(medicalHistory2);

        // Create Doctor
        doctor = new Doctor(1, "Dr. John Doe", Speciality.PHYSICIAN, null);

        // Create DoctorPatient associations
        doctorPatient1 = new DoctorPatient(doctor, patient1);
        doctorPatient2 = new DoctorPatient(doctor, patient2);
    }
    
    @Test
    public void getPatientsByDoctorId() throws InvalidIDException {
        // Case 1: Getting the correct output
        List<DoctorPatient> doctorPatients = Arrays.asList(doctorPatient1, doctorPatient2);
        
        // Mocking the repository for returning the doctor object.
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(doctorPatientRepository.findByDoctor(doctor)).thenReturn(doctorPatients);

        assertEquals(2, doctorService.getPatientsByDoctorId(1).size());   
        // Create expected list of patients based on doctor-patient mapping
        List<Patient> expectedPatients = Arrays.asList(patient1, patient2);

        // Call service and capture the actual output
        List<Patient> actualPatients = doctorService.getPatientsByDoctorId(1);

        // Checking that the list returned from the service matches the expected patients
        assertEquals(expectedPatients, actualPatients);
        
        // Case 2: Doctor ID does not exist, should throw InvalidIDException
        when(doctorRepository.findById(999)).thenReturn(Optional.empty());
        
        // Expecting InvalidIDException to be thrown
        try {
            doctorService.getPatientsByDoctorId(999);
        } catch (InvalidIDException e) {
            assertEquals("Doctor Id is inavlid!!!", e.getMessage());
        }
    }
    
    
     
}
