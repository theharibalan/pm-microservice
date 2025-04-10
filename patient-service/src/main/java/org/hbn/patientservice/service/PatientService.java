package org.hbn.patientservice.service;

import org.hbn.patientservice.dto.PatientRequestDTO;
import org.hbn.patientservice.dto.PatientResponseDTO;
import org.hbn.patientservice.exception.EmailAlreadyExistException;
import org.hbn.patientservice.exception.PatientNotFoundException;
import org.hbn.patientservice.grpc.BillingServiceGrpcClient;
import org.hbn.patientservice.mapper.PatientMapper;
import org.hbn.patientservice.model.Patient;
import org.hbn.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    //@Autowired
    private PatientRepository patientRepository;

    private final BillingServiceGrpcClient billingServiceGrpcClient;

    // we injected using constructor
    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.patientRepository = patientRepository;
    }

    // we replace with autowired
//    public PatientService(PatientRepository patientRepository) {
//        this.patientRepository = patientRepository;
//    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients
                .stream()
                .map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("A Patient with the email address '" + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, @Validated PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found by id: " + id));


        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id
        )) {
            throw new EmailAlreadyExistException(
                    "A patient with this email " + "already exists"
                            + patientRequestDTO.getEmail());
        }


        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
