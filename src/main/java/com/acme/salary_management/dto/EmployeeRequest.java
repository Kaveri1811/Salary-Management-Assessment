
package com.acme.salary_management.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {

    @NotBlank
    private String employeeCode;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Positive
    private Long departmentId;

    @NotBlank
    private String jobTitle;

    @NotBlank
    private String country;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$",
             message = "Currency must be a 3-letter uppercase code")
    private String currency;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal annualSalary;

    @NotBlank
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$")
    private String employmentStatus;
}
