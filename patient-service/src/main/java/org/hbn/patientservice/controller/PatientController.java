package org.hbn.patientservice.controller;

import org.hbn.patientservice.dto.PatientResponseDTO;
import org.hbn.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
