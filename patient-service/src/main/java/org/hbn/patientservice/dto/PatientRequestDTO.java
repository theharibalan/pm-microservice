package org.hbn.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hbn.patientservice.dto.validators.CreatePatientValidationGroup;

@Data
public class PatientRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(max=100, message = "Name cannot exceed 100 character")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth;

    @NotBlank (groups = CreatePatientValidationGroup.class, message = "Registered date is required")
    private String registeredDate;
}
