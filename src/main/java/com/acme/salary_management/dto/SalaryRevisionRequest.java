package com.acme.salary_management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalaryRevisionRequest {

    @NotNull
    @DecimalMin(value = "0.01", message = "Salary must be greater than zero")
    private BigDecimal newSalary;

    @NotNull
    private LocalDate effectiveDate;

    @NotBlank
    private String reason;
}