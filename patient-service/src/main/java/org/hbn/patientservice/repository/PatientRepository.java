package org.hbn.patientservice.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hbn.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(@NotNull @Email String email);
    boolean existsByEmailAndIdNot(@NotNull @Email String email, UUID id);
}
