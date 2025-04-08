package org.hbn.patientservice.controller;

import jakarta.validation.Valid;
import org.hbn.patientservice.dto.PatientRequestDTO;
import org.hbn.patientservice.dto.PatientResponseDTO;
import org.hbn.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

//    public PatientController(PatientService patientService) {
//        this.patientService = patientService;
//    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patientDTOs = patientService.getPatients();
        return new ResponseEntity<>(patientDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO newPatient = patientService.createPatient(patientRequestDTO);

        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }
}
