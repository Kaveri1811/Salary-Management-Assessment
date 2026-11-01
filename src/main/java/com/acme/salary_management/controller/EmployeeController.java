
package com.acme.salary_management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.salary_management.dto.EmployeeRequest;
import com.acme.salary_management.dto.SalaryRevisionRequest;
import com.acme.salary_management.entity.Employee;
import com.acme.salary_management.entity.SalaryHistory;
import com.acme.salary_management.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        Employee employee =
                employeeService.createEmployee(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employee);
    }

    @GetMapping
public Page<Employee> getAllEmployees(

        @RequestParam(required = false) String search,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) Long departmentId,
        @RequestParam(required = false) String status,

        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    if (page < 0 || size < 1 || size > 100) {
        throw new IllegalArgumentException(
                "Page must be >= 0 and size must be between 1 and 100"
        );
    }

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by("id").descending()
    );

    return employeeService.getAllEmployees(
            search, country, departmentId, status, pageable
    );
}

@GetMapping("/{id}")
public Employee getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id);
}

@PutMapping("/{id}")
public ResponseEntity<Employee> updateEmployee(
        @PathVariable Long id,
        @Valid @RequestBody EmployeeRequest request) {

    Employee updatedEmployee = employeeService.updateEmployee(id, request);

    return ResponseEntity.ok(updatedEmployee);
}

@PatchMapping("/{id}/status")
public ResponseEntity<Employee> updateEmployeeStatus(
        @PathVariable Long id,
        @RequestParam String status) {

    Employee updatedEmployee =
            employeeService.updateEmployeeStatus(id, status);

    return ResponseEntity.ok(updatedEmployee);
}

@PostMapping("/{id}/salary-revisions")
public ResponseEntity<SalaryHistory> reviseSalary(
        @PathVariable Long id,
        @Valid @RequestBody SalaryRevisionRequest request) {

    SalaryHistory history =
            employeeService.reviseSalary(id, request);

    return ResponseEntity.status(HttpStatus.CREATED).body(history);
}

@GetMapping("/{id}/salary-revisions")
public ResponseEntity<List<SalaryHistory>> getSalaryHistory(
        @PathVariable Long id) {

    return ResponseEntity.ok(
            employeeService.getSalaryHistory(id)
    );
}
}
